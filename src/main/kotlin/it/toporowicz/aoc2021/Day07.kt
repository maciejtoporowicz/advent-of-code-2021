package it.toporowicz.aoc2021

import kotlin.math.abs

typealias AmountOfFuel = Int
typealias HorizontalPosition = Int

typealias FuelCalculationAlgorithm = (positionDelta: HorizontalPosition) -> AmountOfFuel

fun main() {
    val inputPositions = InputReader.readInputData("it/toporowicz/aoc2021/day07.txt")
        .let { Day07InputDataParser().parse(it) }

    println("Part 1 result: ${Day07().alignForPart1(inputPositions)}")
    println("Part 2 result: ${Day07().alignForPart2(inputPositions)}")
}

class Day07InputDataParser {
    fun parse(lines: List<String>): List<HorizontalPosition> =
        lines
            .first()
            .split(",")
            .map { Integer.parseInt(it) }
}

class Day07 {
    fun alignForPart1(crabPositions: List<HorizontalPosition>) = align(crabPositions, calculateAmountOfFuelForPart01)

    fun alignForPart2(crabPositions: List<HorizontalPosition>) = align(crabPositions, calculateAmountOfFuelForPart02)

    private fun align(
        crabPositions: List<HorizontalPosition>,
        fuelCalculationAlgorithm: FuelCalculationAlgorithm
    ): AmountOfFuel {
        val crabPositionStatistics = crabPositions.stream()
            .mapToInt { it }
            .summaryStatistics()

        return (crabPositionStatistics.min..crabPositionStatistics.max)
            .map { targetPositionToCheck -> align(crabPositions, targetPositionToCheck, fuelCalculationAlgorithm) }
            .stream()
            .mapToInt { it }
            .min()
            .orElseThrow { RuntimeException("No results") }
    }

    private fun align(
        crabPositions: List<HorizontalPosition>,
        targetPosition: HorizontalPosition,
        fuelCalculationAlgorithm: FuelCalculationAlgorithm
    ): AmountOfFuel {
        return crabPositions
            .fold(0) { accumulatedFuel, crabPosition ->
                val positionDelta = abs(crabPosition - targetPosition)
                accumulatedFuel + fuelCalculationAlgorithm(positionDelta)
            }
    }

    private val calculateAmountOfFuelForPart01: FuelCalculationAlgorithm = { positionDelta -> positionDelta }

    private val calculateAmountOfFuelForPart02: FuelCalculationAlgorithm = { positionDelta ->
        if (positionDelta == 0) 0 else (1..positionDelta).sum()
    }
}