package it.toporowicz.aoc2021

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals


internal class Day11Test {
    @Test
    fun shouldRead5x5() {
        // given
        val lines = listOf(
            "11111",
            "19991",
            "19191",
            "19991",
            "11111"
        )

        // when
        val actualMatrix = Day11InputDataParser().parse(lines)

        // then
        val expectedMatrix = Day11.OctopusMatrix(
            mapOf(
                Day11.Coordinate(0, 0) to Day11.Octopus(1, 0),
                Day11.Coordinate(0, 1) to Day11.Octopus(1, 0),
                Day11.Coordinate(0, 2) to Day11.Octopus(1, 0),
                Day11.Coordinate(0, 3) to Day11.Octopus(1, 0),
                Day11.Coordinate(0, 4) to Day11.Octopus(1, 0),
                Day11.Coordinate(1, 0) to Day11.Octopus(1, 0),
                Day11.Coordinate(1, 1) to Day11.Octopus(9, 0),
                Day11.Coordinate(1, 2) to Day11.Octopus(9, 0),
                Day11.Coordinate(1, 3) to Day11.Octopus(9, 0),
                Day11.Coordinate(1, 4) to Day11.Octopus(1, 0),
                Day11.Coordinate(2, 0) to Day11.Octopus(1, 0),
                Day11.Coordinate(2, 1) to Day11.Octopus(9, 0),
                Day11.Coordinate(2, 2) to Day11.Octopus(1, 0),
                Day11.Coordinate(2, 3) to Day11.Octopus(9, 0),
                Day11.Coordinate(2, 4) to Day11.Octopus(1, 0),
                Day11.Coordinate(3, 0) to Day11.Octopus(1, 0),
                Day11.Coordinate(3, 1) to Day11.Octopus(9, 0),
                Day11.Coordinate(3, 2) to Day11.Octopus(9, 0),
                Day11.Coordinate(3, 3) to Day11.Octopus(9, 0),
                Day11.Coordinate(3, 4) to Day11.Octopus(1, 0),
                Day11.Coordinate(4, 0) to Day11.Octopus(1, 0),
                Day11.Coordinate(4, 1) to Day11.Octopus(1, 0),
                Day11.Coordinate(4, 2) to Day11.Octopus(1, 0),
                Day11.Coordinate(4, 3) to Day11.Octopus(1, 0),
                Day11.Coordinate(4, 4) to Day11.Octopus(1, 0)
            )
        )
        assertEquals(expectedMatrix, actualMatrix)
    }

    @ParameterizedTest
    @MethodSource("testDataForCounting10x10Flashes")
    fun shouldCountFlashesDays10x10(howManySteps: Int, expectedFlashes: Long) {
        // given
        val matrix = Day11InputDataParser().parse(
            InputReader.readInputData(
                "it/toporowicz/aoc2021/day11.txt",
                Day11Test::class.java
            )
        )

        // when
        val actualFlashes = Day11().countFlashes(matrix, howManySteps)

        // then
        assertEquals(expectedFlashes, actualFlashes)
    }

    @Test
    fun shouldCountFlashes5x5() {
        // given
        val matrix = Day11InputDataParser().parse(
            listOf(
                "11111",
                "19991",
                "19191",
                "19991",
                "11111"
            )
        )

        // when
        val actualFlashes = Day11().countFlashes(matrix, 2)

        // then
        assertEquals(9, actualFlashes)
    }

    companion object {
        @JvmStatic
        fun testDataForCounting10x10Flashes() = Stream.of(
            Arguments.of(0, 0),
            Arguments.of(1, 0),
            Arguments.of(2, 35),
            Arguments.of(3, 35 + 45),
            Arguments.of(4, 35 + 45 + 16),
            Arguments.of(5, 35 + 45 + 16 + 8),
            Arguments.of(6, 35 + 45 + 16 + 8 + 1),
            Arguments.of(7, 35 + 45 + 16 + 8 + 1 + 7),
            Arguments.of(8, 35 + 45 + 16 + 8 + 1 + 7 + 24),
            Arguments.of(9, 35 + 45 + 16 + 8 + 1 + 7 + 24 + 39),
            Arguments.of(10, 204),
            Arguments.of(100, 1656)
        )
    }
}