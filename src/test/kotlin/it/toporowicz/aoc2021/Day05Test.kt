package it.toporowicz.aoc2021

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day05Test {
    private val expectedTestData = listOf(
        Day05.Line(Day05.Point(0, 9), Day05.Point(5, 9)),
        Day05.Line(Day05.Point(8, 0), Day05.Point(0, 8)),
        Day05.Line(Day05.Point(9, 4), Day05.Point(3, 4)),
        Day05.Line(Day05.Point(2, 2), Day05.Point(2, 1)),
        Day05.Line(Day05.Point(7, 0), Day05.Point(7, 4)),
        Day05.Line(Day05.Point(6, 4), Day05.Point(2, 0)),
        Day05.Line(Day05.Point(0, 9), Day05.Point(2, 9)),
        Day05.Line(Day05.Point(3, 4), Day05.Point(1, 4)),
        Day05.Line(Day05.Point(0, 0), Day05.Point(8, 8)),
        Day05.Line(Day05.Point(5, 5), Day05.Point(8, 2))
    )

    @Test
    fun shouldParseTestData() {
        // given
        val inputLines = InputReader.readInputData("it/toporowicz/aoc2021/day05.txt", Day05Test::class.java)

        // when
        val actualData = Day05.Day05DataParser().parse(inputLines)

        // then
        assertEquals(expectedTestData, actualData)
    }

    @Test
    fun shouldSayHowManyPointsWhereAtLeastTwoLinesOverlap() {
        // when
        val actualCount = Day05().part01(expectedTestData)

        // then
        assertEquals(5, actualCount)
    }

    @Test
    fun shouldSayHowManyPointsWhereAtLeastTwoLinesOverlapWhenDiagonalLinesCountToo() {
        // when
        val actualCount = Day05().part02(expectedTestData)

        // then
        assertEquals(12, actualCount)
    }

    @Test
    fun shouldContain() {
        val line = Day05.Line(a = Day05.Point(x = 2, y = 2), b = Day05.Point(x = 2, y = 1))

        assertTrue { line.contains(Day05.Point(2, 1)) }
    }
}