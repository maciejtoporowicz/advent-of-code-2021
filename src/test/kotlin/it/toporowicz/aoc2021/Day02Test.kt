package it.toporowicz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Day02Test {
    @ParameterizedTest
    @MethodSource("testForMultiplyingFinalPosition")
    fun shouldMultiplyFinalHorizontalPositionByFinalDepth(instructions: List<String>, expectedOutcome: Int) {
        // when
        val actualOutcome = Day02().part01(instructions)

        // then
        assertEquals(expectedOutcome, actualOutcome)
    }

    @ParameterizedTest
    @MethodSource("testForMultiplyingFinalPositionTakingAimIntoAccount")
    fun shouldMultiplyFinalHorizontalPositionByFinalDepthTakingAimIntoAccount(instructions: List<String>, expectedOutcome: Int) {
        // when
        val actualOutcome = Day02().part02(instructions)

        // then
        assertEquals(expectedOutcome, actualOutcome)
    }

    companion object TestData {
        @JvmStatic
        fun testForMultiplyingFinalPosition() =
            listOf(
                Arguments.of(
                    emptyList<String>(),
                    0
                ),
                Arguments.of(
                    listOf(
                        "forward 5"
                    ),
                    0
                ),
                Arguments.of(
                    listOf(
                        "up 5"
                    ),
                    0
                ),
                Arguments.of(
                    listOf(
                        "down 5"
                    ),
                    0
                ),
                Arguments.of(
                    listOf(
                        "forward 5",
                        "down 5"
                    ),
                    25
                ),
                Arguments.of(
                    listOf(
                        "forward 5",
                        "down 5",
                        "forward 8",
                        "up 3",
                        "down 8",
                        "forward 2"
                    ),
                    150
                )
            )

        @JvmStatic
        fun testForMultiplyingFinalPositionTakingAimIntoAccount() =
            listOf(
                Arguments.of(
                    emptyList<String>(),
                    0
                ),
                Arguments.of(
                    listOf(
                        "forward 5"
                    ),
                    0
                ),
                Arguments.of(
                    listOf(
                        "up 5"
                    ),
                    0
                ),
                Arguments.of(
                    listOf(
                        "down 5"
                    ),
                    0
                ),
                Arguments.of(
                    listOf(
                        "forward 5",
                        "down 5"
                    ),
                    0
                ),
                Arguments.of(
                    listOf(
                        "forward 5",
                        "down 5",
                        "forward 8"
                    ),
                    520
                ),
                Arguments.of(
                    listOf(
                        "forward 5",
                        "down 5",
                        "forward 8",
                        "up 3",
                        "down 8",
                        "forward 2"
                    ),
                    900
                )
            )
    }
}