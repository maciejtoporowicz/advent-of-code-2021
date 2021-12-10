package it.toporowicz.aoc2021

fun main() {
    val inputMeasurements = InputReader.readInputData("day02.txt")
        .map { numberAsString -> Integer.parseInt(numberAsString) }

    println("Part 1 result: ${Day01().part01(inputMeasurements)}")
    println("Part 2 result: ${Day01().part02(inputMeasurements)}")
}

class Day01 {

    fun part01(depthMeasurements: List<Int>) = howManyTimesDepthIncreasedWithin(depthMeasurements)

    fun part02(depthMeasurements: List<Int>): Int {
        val slidingWindows = sumsOfSlidingWindows(depthMeasurements)
        return howManyTimesDepthIncreasedWithin(slidingWindows)
    }

    companion object Calculations {
        fun sumsOfSlidingWindows(depthMeasurements: List<Int>, windowSize: Int = 3): List<Int> {
            return sumOfSlidingWindow(depthMeasurements.take(windowSize), depthMeasurements.drop(1), windowSize)
        }

        private fun sumOfSlidingWindow(
            currentWindow: List<Int>,
            remainingMeasurements: List<Int>,
            windowSize: Int
        ): List<Int> {
            val minimumRemainingMeasurements = windowSize - 1

            if (remainingMeasurements.size < minimumRemainingMeasurements) {
                return emptyList()
            }
            return listOf(currentWindow.sum())
                .plus(
                    sumOfSlidingWindow(
                        remainingMeasurements.take(windowSize),
                        remainingMeasurements.drop(1),
                        windowSize
                    )
                )
        }

        fun howManyTimesDepthIncreasedWithin(depthMeasurements: List<Int>): Int {
            return howManyIncreasesLeft(depthMeasurements.first(), depthMeasurements.drop(1))
        }

        private fun howManyIncreasesLeft(currentMeasurement: Int, remainingMeasurements: List<Int>): Int {
            if (remainingMeasurements.isEmpty()) {
                return 0
            }

            val nextMeasurement = remainingMeasurements.first()

            return (if (currentMeasurement < nextMeasurement) 1 else 0) + howManyIncreasesLeft(
                nextMeasurement,
                remainingMeasurements.drop(1)
            )
        }
    }
}