package it.toporowicz.aoc2021

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val expectedMap = HeightMap(
        listOf(
            listOf(2, 1, 9, 9, 9, 4, 3, 2, 1, 0),
            listOf(3, 9, 8, 7, 8, 9, 4, 9, 2, 1),
            listOf(9, 8, 5, 6, 7, 8, 9, 8, 9, 2),
            listOf(8, 7, 6, 7, 8, 9, 6, 7, 8, 9),
            listOf(9, 8, 9, 9, 9, 6, 5, 6, 7, 8)
        )
    )

    @Test
    fun shouldParseInput() {
        // given
        val inputLines = InputReader.readInputData("it/toporowicz/aoc2021/day09.txt", Day09Test::class.java)

        // when
        val actualMap = Day09InputDataParser().parse(inputLines)

        // then
        assertEquals(expectedMap, actualMap)
    }

    @Test
    fun shouldCountSumForPart1() {
        // when
        val actualSum = Day09().sumRiskLevelOfLowPoints(expectedMap)

        // then
        assertEquals(15, actualSum)
    }

    @Test
    fun shouldMultiplySizesOfThreeBiggestBasins() {
        // when
        val actualResult = Day09().multiplySizesOfThreeBiggestBasins(expectedMap)

        // then
        assertEquals(1134, actualResult)
    }
}