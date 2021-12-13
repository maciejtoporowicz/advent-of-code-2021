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
            (MIN_INTERNAL_TIMER..MAX_INTERNAL_TIMER).associateWith { internalTimerValue ->
                allFish.count { it.internalTimer == internalTimerValue }.toLong()
            }
        )

        fun size() = numberOfFishByInternalTimer.values.sum()

        fun oneDayForward() = FishSchool(
            numberOfFishByInternalTimer
                .toMutableMap()
                .also {
                    val numberOfNewbornFish = it.getValue(MIN_INTERNAL_TIMER)

                    it.keys.sorted()
                        .drop(DROP_FISH_WHO_ARE_ABOUT_TO_BEAR_A_FISH)
                        .forEach { internalTimerValue ->
                            it[internalTimerValue.dec()] = it.getValue(internalTimerValue)
                        }

                    it.computeIfPresent(
                        INTERNAL_TIMER_FOR_FISH_WHO_JUST_BORE_ANOTHER_FISH
                    ) { _, currentNumberOfFish -> currentNumberOfFish + numberOfNewbornFish }

                    it[INTERNAL_TIMER_FOR_NEWBORN_FISH] = numberOfNewbornFish
                }
                .toMap()
        )
    }

    data class Fish(val internalTimer: Int)

    companion object {
        private const val DROP_FISH_WHO_ARE_ABOUT_TO_BEAR_A_FISH = 1
        private const val INTERNAL_TIMER_FOR_FISH_WHO_JUST_BORE_ANOTHER_FISH = 6
        private const val INTERNAL_TIMER_FOR_NEWBORN_FISH = 8
        private const val MIN_INTERNAL_TIMER = 0
        private const val MAX_INTERNAL_TIMER = INTERNAL_TIMER_FOR_NEWBORN_FISH
    }
}