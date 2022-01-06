package it.toporowicz.aoc2021

import java.math.BigInteger

fun main() {
    val inputData = InputReader.readInputData("it/toporowicz/aoc2021/day14.txt").let {
        Day14InputDataParser().parse(it)
    }

    println("Part 1 result: ${Day14().differenceBetweenMostAndLeastFrequentElementsAfterPolymerization(10, inputData)}")
    println("Part 2 result: ${Day14().differenceBetweenMostAndLeastFrequentElementsAfterPolymerization(40, inputData)}")
}

class Day14InputDataParser {
    fun parse(lines: List<String>): Day14.InputData {
        val polymerTemplate = lines[0]

        val pairInsertionRules = lines
            .drop(2)
            .map { line ->
                line.split(" -> ")
                    .let {
                        val rawAdjacentElements = it[0]
                        val elementToInsert = it[1].toCharArray().first()

                        Day14.AdjacentElements(
                            rawAdjacentElements[0],
                            rawAdjacentElements[1]
                        ) to elementToInsert
                    }
            }.toMap()

        return Day14.InputData(Day14.Polymer(polymerTemplate.toCharArray().toList()), pairInsertionRules)
    }
}

class Day14 {
    fun differenceBetweenMostAndLeastFrequentElementsAfterPolymerization(numOfSteps: Int, inputData: InputData): BigInteger {
        val (polymer, pairInsertionRules) = inputData

        val polymerAfterPolymerization = (1..numOfSteps).fold(
            polymer
        ) { accPolymer, _ -> accPolymer.step(pairInsertionRules) }

        val elementFrequencies = polymerAfterPolymerization.countElementFrequency().values

        val min = elementFrequencies.minOf { it }
        val max = elementFrequencies.maxOf { it }

        return max - min
    }

    data class AdjacentElements(val first: Char, val second: Char)

    data class InputData(val polymerTemplate: Polymer, val pairInsertionRules: Map<AdjacentElements, Char>)

    data class Polymer(val template: List<Char>) {
        init {
            require(template.size >= 2) { "Template needs to have at least two elements" }
        }

        fun step(pairInsertionRules: Map<AdjacentElements, Char>): Polymer {
            val untilIndexOfSecondFromEnd = template.size - 2

            val allMatchingInsertionIndexForElement =
                (0..untilIndexOfSecondFromEnd)
                    .mapNotNull { index ->
                        val nextTwoElements = AdjacentElements(template[index], template[index + 1])
                        val matchingPairInsertionRule = pairInsertionRules[nextTwoElements]!!

                        (index + 1) to matchingPairInsertionRule
                    }

            return allMatchingInsertionIndexForElement
                .reversed()
                .fold(template.toMutableList()) { accTemplate, insertionIndexForElement ->
                    val (insertionIndex, element) = insertionIndexForElement
                    accTemplate.add(insertionIndex, element)
                    accTemplate
                }
                .let { Polymer(it.toList()) }
        }

        fun countElementFrequency(): Map<Char, BigInteger> = template
            .toCharArray()
            .fold<MutableMap<Char, BigInteger>>(mutableMapOf()) { accFrequency, element ->
                accFrequency.merge(element, BigInteger.ONE) { existingFrequency, elementToMerge -> existingFrequency + elementToMerge }
                accFrequency
            }.toMap()
    }
}