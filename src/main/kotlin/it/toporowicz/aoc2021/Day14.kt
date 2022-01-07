package it.toporowicz.aoc2021

fun main() {
    val inputData = InputReader.readInputData("it/toporowicz/aoc2021/day14.txt").let {
        Day14InputDataParser().parse(it)
    }

    println("Part 1 result: ${Day14().differenceBetweenMostAndLeastFrequentElementsAfterPolymerization(10, inputData)}")
    println("Part 2 result: ${Day14().differenceBetweenMostAndLeastFrequentElementsAfterPolymerization(40, inputData)}")
}

class Day14InputDataParser {
    fun parse(lines: List<String>): Day14.InputData {
        val startingTemplateAsString = lines[0]

        val untilSecondIndexFromEnd = startingTemplateAsString.length - 2

        val characterBasedPairInsertionRules = lines
            .drop(2)
            .associate { line ->
                line.split(" -> ")
                    .let {
                        val adjacentElements = it[0]
                        val elementToInsert = it[1].toCharArray().first()

                        Day14.AdjacentElements(
                            adjacentElements[0],
                            adjacentElements[1]
                        ) to elementToInsert
                    }
            }

        val pairInsertionRules = characterBasedPairInsertionRules.map { (adjacentElements, element) ->
            val leftResultingElement = Day14.AdjacentElements(adjacentElements.left, element)
            val rightResultingElement = Day14.AdjacentElements(element, adjacentElements.right)

            adjacentElements to Day14.ResultAdjacentElements(
                leftResultingElement,
                rightResultingElement
            )
        }.toMap()

        val adjacentElements =
            (0..untilSecondIndexFromEnd)
                .fold(listOf<Day14.AdjacentElements>()) { accList, index ->
                    accList.plus(
                        Day14.AdjacentElements(
                            startingTemplateAsString[index],
                            startingTemplateAsString[index + 1]
                        )
                    )
                }


        return Day14.InputData(Day14.Polymer(adjacentElements), pairInsertionRules)
    }
}

class Day14 {
    fun differenceBetweenMostAndLeastFrequentElementsAfterPolymerization(
        numOfSteps: Int,
        inputData: InputData
    ): Long {
        val (polymer, pairInsertionRules) = inputData

        val lastLetter = polymer.template.last().right

        val result = mutableMapOf<AdjacentElements, Long>()
            .also { map ->
                pairInsertionRules.keys.forEach { adjacentElements ->
                    map[adjacentElements] = 0
                }
            }

        polymer.template.forEach {
            result[it] = result[it]!! + 1
        }

        repeat(numOfSteps) {
            val delta = result.keys.associateWith { 0L }.toMutableMap()

            result.keys.forEach { adjacentElements ->
                val adjacentElementsCount = result[adjacentElements]!!

                if (adjacentElementsCount <= 0L) {
                    return@forEach
                }

                val (left, right) = pairInsertionRules[adjacentElements]!!
                delta[adjacentElements] = delta[adjacentElements]!! - adjacentElementsCount
                delta[left] = delta[left]!! + adjacentElementsCount
                delta[right] = delta[right]!! + adjacentElementsCount
            }

            delta.forEach { (deltaKey, deltaValue) ->
                result[deltaKey] = result[deltaKey]!! + deltaValue
            }
        }

        val elementFrequencies = countElementFrequency(result, lastLetter).values

        val min = elementFrequencies.minOf { it }
        val max = elementFrequencies.maxOf { it }

        return max - min
    }

    private fun countElementFrequency(
        result: Map<AdjacentElements, Long>,
        lastLetter: Char
    ): Map<Char, Long> {
        val accMap = mutableMapOf<Char, Long>()

        result.forEach { (adjacentElements, count) ->
            val character = adjacentElements.left

            accMap.merge(character, count) { oldCount, newCount -> oldCount + newCount }
        }

        accMap.merge(lastLetter, 1) { oldCount, newCount -> oldCount + newCount }

        return accMap.toMap()
    }

    data class AdjacentElements(val left: Char, val right: Char)
    data class ResultAdjacentElements(val left: AdjacentElements, val right: AdjacentElements)

    data class InputData(
        val polymerTemplate: Polymer,
        val pairInsertionRules: Map<AdjacentElements, ResultAdjacentElements>
    )

    data class Polymer(val template: List<AdjacentElements>)
}