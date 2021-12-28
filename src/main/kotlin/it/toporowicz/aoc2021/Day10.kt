package it.toporowicz.aoc2021

import java.util.*

fun main() {
    val lines = InputReader.readInputData("it/toporowicz/aoc2021/day10.txt")

    println("Part 1 result: ${Day10().totalSyntaxErrorScoreForCorruptedLines(lines)}")
    println("Part 2 result: ${Day10().totalAutocompleteScoreForIncompleteLines(lines)}")
}

interface Result {
    val line: String
}

data class Incomplete(override val line: String, val openingCharStack: Stack<Char>) : Result
data class Complete(override val line: String) : Result
data class Corrupted(override val line: String, val expectedChar: Char, val foundChar: Char) : Result

class Day10 {
    data class MatchingCharacter(val openingCharacter: Char, val closingCharacter: Char)
    data class Score(val scoreForWrongSyntax: Long, val scoreForAutocomplete: Long)

    data class CharacterConfig(private val scoreForWrongSyntaxByMatchingCharacter: Map<MatchingCharacter, Score>) {
        private val matchingCharacterByOpeningCharacter =
            scoreForWrongSyntaxByMatchingCharacter.keys.associateBy { it.openingCharacter }
        private val matchingCharacterByClosingCharacter =
            scoreForWrongSyntaxByMatchingCharacter.keys.associateBy { it.closingCharacter }
        private val scoreByClosingCharacter = scoreForWrongSyntaxByMatchingCharacter
            .mapKeys { it.key.closingCharacter }

        fun isOpeningCharacter(char: Char) = matchingCharacterByOpeningCharacter.containsKey(char)

        fun isClosingCharacter(char: Char) = matchingCharacterByClosingCharacter.containsKey(char)

        fun matchingClosingCharacterFor(openingChar: Char): Char {
            require(isOpeningCharacter(openingChar))
            return matchingCharacterByOpeningCharacter[openingChar]!!.closingCharacter
        }

        fun scoreForWrongSyntaxForCharacter(closingChar: Char): Long {
            require(isClosingCharacter(closingChar))
            return scoreByClosingCharacter[closingChar]!!.scoreForWrongSyntax
        }

        fun scoreForAutocompleteForCharacter(closingChar: Char): Long {
            require(isClosingCharacter(closingChar))
            return scoreByClosingCharacter[closingChar]!!.scoreForAutocomplete
        }
    }

    private val charactersConfig = CharacterConfig(
        mapOf(
            MatchingCharacter('(', ')') to Score(3, 1),
            MatchingCharacter('[', ']') to Score(57, 2),
            MatchingCharacter('{', '}') to Score(1197, 3),
            MatchingCharacter('<', '>') to Score(25137, 4)
        )
    )

    fun totalSyntaxErrorScoreForCorruptedLines(lines: List<String>): Long {
        return lines
            .map { analyze(it) }
            .filterIsInstance<Corrupted>()
            .map { it.foundChar }
            .sumOf { charactersConfig.scoreForWrongSyntaxForCharacter(it) }
    }

    fun totalAutocompleteScoreForIncompleteLines(lines: List<String>): Long {
        return lines
            .asSequence()
            .map { analyze(it) }
            .filterIsInstance<Incomplete>()
            .map { autocomplete(it.openingCharStack) }
            .map {
                it.fold(0L) { total, currentChar ->
                    (total * 5L) + charactersConfig.scoreForAutocompleteForCharacter(currentChar)
                }
            }
            .sorted()
            .toList()
            .let {
                val middleScoreIndex = (it.size - 1) / 2
                it[middleScoreIndex]
            }
    }

    private fun autocomplete(openingCharStack: Stack<Char>): List<Char> {
        val autocompleteChars = mutableListOf<Char>()

        while (!openingCharStack.isEmpty()) {
            autocompleteChars.add(charactersConfig.matchingClosingCharacterFor(openingCharStack.pop()))
        }

        return autocompleteChars.toList()
    }

    private fun analyze(line: String): Result {
        val openingCharacterStack = Stack<Char>()

        for (currentCharacter in line.toCharArray()) {
            if (charactersConfig.isOpeningCharacter(currentCharacter)) {
                openingCharacterStack.push(currentCharacter)
                continue
            }

            if (charactersConfig.isClosingCharacter(currentCharacter)) {
                val lastOpeningChar = openingCharacterStack.peek()
                val expectedClosingChar = charactersConfig.matchingClosingCharacterFor(lastOpeningChar)

                if (currentCharacter != expectedClosingChar) {
                    return Corrupted(line = line, expectedChar = expectedClosingChar, foundChar = currentCharacter)
                }

                openingCharacterStack.pop()
                continue
            }

            throw RuntimeException("Unexpected char: $currentCharacter")
        }

        return if (openingCharacterStack.isEmpty()) Complete(line) else Incomplete(line, openingCharacterStack)
    }
}