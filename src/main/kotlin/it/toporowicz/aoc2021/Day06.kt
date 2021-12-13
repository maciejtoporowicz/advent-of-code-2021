package it.toporowicz.aoc2021

fun main() {
    val fishSchool = InputReader.readInputData("it/toporowicz/aoc2021/day06.txt")
        .let { FishSchoolParser().parse(it) }

    println("Part 1 result: ${Day06().part01(fishSchool, daysToForward = 80)}")
    println("Part 2 result: ${Day06().part01(fishSchool, daysToForward = 256)}")
}

class FishSchoolParser {
    fun parse(inputDataLines: List<String>) =
        inputDataLines
            .flatMap { it.split(",") }
            .map { Integer.parseInt(it) }
            .map { Day06.Fish(it) }
            .let { Day06.FishSchool(it) }
}

typealias InternalTimer = Int
typealias NumberOfFish = Long

class Day06 {
    fun part01(initialFishSchool: FishSchool, daysToForward: Int): Long {
        return (1..daysToForward)
            .fold(initialFishSchool) { accumulatedFishSchool, _ -> accumulatedFishSchool.oneDayForward() }
            .size()
    }

    data class FishSchool constructor(private val numberOfFishByInternalTimer: Map<InternalTimer, NumberOfFish>) {
        constructor(allFish: List<Fish>) : this(
            (0..8).associateWith { internalTimerValue ->
                allFish.count { it.internalTimer == internalTimerValue }.toLong()
            }
        )

        fun size() = numberOfFishByInternalTimer.values.sum()

        fun oneDayForward() = FishSchool(
            numberOfFishByInternalTimer
                .toMutableMap()
                .also {
                    val numberOfNewbornFish = it.getValue(0)

                    (1..8).forEach { internalTimerValue ->
                        it[internalTimerValue - 1] = it.getValue(internalTimerValue)
                    }
                    it[6] = it.getValue(6) + numberOfNewbornFish
                    it[8] = numberOfNewbornFish
                }
                .toMap()
        )
    }

    data class Fish(val internalTimer: Int)
}