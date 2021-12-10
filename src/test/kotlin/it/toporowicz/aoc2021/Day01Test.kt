package it.toporowicz.aoc2021

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class Day01Test {
    @ParameterizedTest
    @MethodSource("testCasesForPart01")
    fun shouldCountIncrements(depthMeasurements: List<Int>, expectedCount: Int) {
        // when
        val actualCount = Day01.Calculations.howManyTimesDepthIncreasedWithin(depthMeasurements)

        // then
        assertEquals(expectedCount, actualCount)
    }

    @ParameterizedTest
    @MethodSource("testCasesForPart02")
    fun shouldSumSlidingWindows(depthMeasurements: List<Int>, expectedCount: Int) {
        // when
        val actualCount = Day01().part02(depthMeasurements)

        // then
        assertEquals(expectedCount, actualCount)
    }

    companion object {
        @JvmStatic
        fun testCasesForPart01() = listOf(
            Arguments.of(listOf(123), 0),
            Arguments.of(listOf(123, 123), 0),
            Arguments.of(listOf(123, 234), 1),
            Arguments.of(listOf(123, 234, 345), 2),
            Arguments.of(listOf(123, 234, 345, 123), 2),
            Arguments.of(listOf(123, 234, 345, 123, 567), 3),
            Arguments.of(listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263), 7)
        )

        @JvmStatic
        fun testCasesForPart02() = listOf(
            Arguments.of(listOf(101, 102, 103), 0),
            Arguments.of(listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263), 5)
        )
    }
}