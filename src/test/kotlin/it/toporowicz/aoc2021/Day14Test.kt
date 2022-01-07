package it.toporowicz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14Test {
    private val pairInsertionRules = mapOf(
        Day14.AdjacentElements('C', 'H') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('C', 'B'),
            Day14.AdjacentElements('B', 'H')
        ),
        Day14.AdjacentElements('H', 'H') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('H', 'N'),
            Day14.AdjacentElements('N', 'H')
        ),
        Day14.AdjacentElements('C', 'B') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('C', 'H'),
            Day14.AdjacentElements('H', 'B')
        ),
        Day14.AdjacentElements('N', 'H') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('N', 'C'),
            Day14.AdjacentElements('C', 'H')
        ),
        Day14.AdjacentElements('H', 'B') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('H', 'C'),
            Day14.AdjacentElements('C', 'B')
        ),
        Day14.AdjacentElements('H', 'C') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('H', 'B'),
            Day14.AdjacentElements('B', 'C')
        ),
        Day14.AdjacentElements('H', 'N') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('H', 'C'),
            Day14.AdjacentElements('C', 'N')
        ),
        Day14.AdjacentElements('N', 'N') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('N', 'C'),
            Day14.AdjacentElements('C', 'N')
        ),
        Day14.AdjacentElements('B', 'H') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('B', 'H'),
            Day14.AdjacentElements('H', 'H')
        ),
        Day14.AdjacentElements('N', 'C') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('N', 'B'),
            Day14.AdjacentElements('B', 'C')
        ),
        Day14.AdjacentElements('N', 'B') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('N', 'B'),
            Day14.AdjacentElements('B', 'B')
        ),
        Day14.AdjacentElements('B', 'N') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('B', 'B'),
            Day14.AdjacentElements('B', 'N')
        ),
        Day14.AdjacentElements('B', 'B') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('B', 'N'),
            Day14.AdjacentElements('N', 'B')
        ),
        Day14.AdjacentElements('B', 'C') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('B', 'B'),
            Day14.AdjacentElements('B', 'C')
        ),
        Day14.AdjacentElements('C', 'C') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('C', 'N'),
            Day14.AdjacentElements('N', 'C')
        ),
        Day14.AdjacentElements('C', 'N') to Day14.ResultAdjacentElements(
            Day14.AdjacentElements('C', 'C'),
            Day14.AdjacentElements('C', 'N')
        ),
    )
    private val expectedTestData = Day14.InputData(
        Day14.Polymer(
            listOf(
                Day14.AdjacentElements('N', 'N'),
                Day14.AdjacentElements('N', 'C'),
                Day14.AdjacentElements('C', 'B')
            )
        ),
        pairInsertionRules
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
        assertEquals(1588, actualDifference)
    }
}