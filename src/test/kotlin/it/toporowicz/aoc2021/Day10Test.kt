package it.toporowicz.aoc2021

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {
    private val inputLines = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]",
    )

    @Test
    fun shouldCountTotalSyntaxErrorScore() {
        // when
        val actualScore = Day10().totalSyntaxErrorScoreForCorruptedLines(inputLines)

        // then
        assertEquals(26397, actualScore)
    }

    @Test
    fun shouldCountTotalAutocompleteScore() {
        // when
        val actualScore = Day10().totalAutocompleteScoreForIncompleteLines(inputLines)

        // then
        assertEquals(288957, actualScore)
    }
}