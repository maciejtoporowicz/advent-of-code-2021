package it.toporowicz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class Day14Test {
    private val expectedTestData = Day14.InputData(
        Day14.Polymer(
            listOf('N', 'N', 'C', 'B')
        ),
        listOf(
            Day14.AdjacentElements('C', 'H') to 'B',
            Day14.AdjacentElements('H', 'H') to 'N',
            Day14.AdjacentElements('C', 'B') to 'H',
            Day14.AdjacentElements('N', 'H') to 'C',
            Day14.AdjacentElements('H', 'B') to 'C',
            Day14.AdjacentElements('H', 'C') to 'B',
            Day14.AdjacentElements('H', 'N') to 'C',
            Day14.AdjacentElements('N', 'N') to 'C',
            Day14.AdjacentElements('B', 'H') to 'H',
            Day14.AdjacentElements('N', 'C') to 'B',
            Day14.AdjacentElements('N', 'B') to 'B',
            Day14.AdjacentElements('B', 'N') to 'B',
            Day14.AdjacentElements('B', 'B') to 'N',
            Day14.AdjacentElements('B', 'C') to 'B',
            Day14.AdjacentElements('C', 'C') to 'N',
            Day14.AdjacentElements('C', 'N') to 'C',
        )
    )

    @Test
    fun shouldParseTestData() {
        // given
        val inputLines = InputReader.readInputData("it/toporowicz/aoc2021/day14.txt", Day14Test::class.java)

        // when
        val actualData = Day14InputDataParser().parse(inputLines)

        // then
        assertEquals(expectedTestData, actualData)
    }

    @Test
    fun shouldCalculateDifference() {
        // when
        val actualDifference =
            Day14().differenceBetweenMostAndLeastFrequentElementsAfterPolymerization(10, expectedTestData)

        // then
        assertEquals(BigInteger.valueOf(1588), actualDifference)
    }
}