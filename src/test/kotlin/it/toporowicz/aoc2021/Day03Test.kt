package it.toporowicz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Day03Test {
    @ParameterizedTest
    @MethodSource("powerConsumptionTestCases")
    fun shouldCalculatePowerConsumptionTestCases(diagnosticReportLines: List<String>, expectedConsumption: Int) {
        // when
        val actualConsumption = Day03().part01(diagnosticReportLines)

        // then
        assertEquals(expectedConsumption, actualConsumption)
    }

    @ParameterizedTest
    @MethodSource("lifeSupportRatingTestCases")
    fun shouldCalculateLifeSupportRatingTestCases(diagnosticReportLines: List<String>, expectedRating: Int) {
        // when
        val actualRating = Day03().part02(diagnosticReportLines)

        // then
        assertEquals(expectedRating, actualRating)
    }

    companion object {
        @JvmStatic
        fun lifeSupportRatingTestCases() = listOf(
            Arguments.of(
                listOf(
                    "1101",
                    "0101",
                    "0111"
                ),
                // oxygen -> 0111 -> 7
                // co2 -> 1101 -> 13
                91
            ),
            Arguments.of(
                listOf(
                    "00100",
                    "11110",
                    "10110",
                    "10111",
                    "10101",
                    "01111",
                    "00111",
                    "11100",
                    "10000",
                    "11001",
                    "00010",
                    "01010"
                ),
                230
            )
        )

        @JvmStatic
        fun powerConsumptionTestCases() = listOf(
            Arguments.of(
                listOf(
                    "1101",
                    "0101",
                    "0101"
                ),
                // most common bits: 0101 -> 5
                // least common bits: 1010 -> 10
                50
            ),
            Arguments.of(
                listOf(
                    "00100",
                    "11110",
                    "10110",
                    "10111",
                    "10101",
                    "01111",
                    "00111",
                    "11100",
                    "10000",
                    "11001",
                    "00010",
                    "01010"
                ),
                198
            )
        )
    }
}