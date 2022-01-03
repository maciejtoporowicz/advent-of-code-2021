package it.toporowicz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13Test {
    private val example = Day13.InputData(
        Day13.TransparentPaper(
            setOf(
                Day13.Coordinate(6,10),
                Day13.Coordinate(0,14),
                Day13.Coordinate(9,10),
                Day13.Coordinate(0,3),
                Day13.Coordinate(10,4),
                Day13.Coordinate(4,11),
                Day13.Coordinate(6,0),
                Day13.Coordinate(6,12),
                Day13.Coordinate(4,1),
                Day13.Coordinate(0,13),
                Day13.Coordinate(10,12),
                Day13.Coordinate(3,4),
                Day13.Coordinate(3,0),
                Day13.Coordinate(8,4),
                Day13.Coordinate(1,10),
                Day13.Coordinate(2,14),
                Day13.Coordinate(8,10),
                Day13.Coordinate(9,0),
            )
        ),
        listOf(
            Day13.FoldY(7),
            Day13.FoldX(5)
        )
    )

    @Test
    fun shouldParseData() {
        // given
        val lines = listOf(
            "6,10",
            "0,14",
            "9,10",
            "",
            "fold along y=7",
            "fold along x=5"
        )

        // when
        val actualData = Day13InputDataParser().parse(lines)

        // then
        val expectedData = Day13.InputData(
            Day13.TransparentPaper(
                setOf(
                    Day13.Coordinate(6, 10),
                    Day13.Coordinate(0, 14),
                    Day13.Coordinate(9, 10),
                )
            ),
            listOf(
                Day13.FoldY(7),
                Day13.FoldX(5)
            )
        )
        assertEquals(expectedData, actualData)
    }

    @Test
    fun shouldCountDotsWhenFoldingLeft() {
        // given
        val data = Day13.InputData(
            Day13.TransparentPaper(
                setOf(
                    Day13.Coordinate(0, 3),
                    Day13.Coordinate(10, 4),
                    Day13.Coordinate(6, 0),
                    Day13.Coordinate(4, 1),
                    Day13.Coordinate(3, 4),
                    Day13.Coordinate(3, 0),
                    Day13.Coordinate(8, 4),
                    Day13.Coordinate(9, 0),
                    Day13.Coordinate(6, 4),
                    Day13.Coordinate(0, 0),
                    Day13.Coordinate(9, 4),
                    Day13.Coordinate(4, 3),
                    Day13.Coordinate(6, 2),
                    Day13.Coordinate(0, 1),
                    Day13.Coordinate(10, 2),
                    Day13.Coordinate(1, 4),
                    Day13.Coordinate(2, 0)
                )
            ),
            listOf(
                Day13.FoldX(5)
            )
        )

        // when
        val actualDots = Day13().countDotsAfterFirstFoldFor(data)

        // then
        assertEquals(16, actualDots)
    }

    @Test
    fun shouldCountDotsWhenFoldingUp() {
        // when
        val actualDots = Day13().countDotsAfterFirstFoldFor(example)

        // then
        assertEquals(17, actualDots)
    }
}