package it.toporowicz.aoc2021

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day07Test {
    private val inputPositionsAsString = "16,1,2,0,4,2,7,1,2,14"
    private val inputPositions = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

    @Test
    fun shouldParsePositions() {
        // when
        val actualInputPositions = Day07InputDataParser().parse(listOf(inputPositionsAsString))

        // then
        assertEquals(inputPositions, actualInputPositions)
    }

    @Test
    fun shouldAlignPositionsForPart01() {
        // when
        val result = Day07().alignForPart1(inputPositions)

        // then
        assertEquals(37, result)
    }

    @Test
    fun shouldAlignPositionsForPart02() {
        // when
        val result = Day07().alignForPart2(inputPositions)

        // then
        assertEquals(168, result)
    }
}