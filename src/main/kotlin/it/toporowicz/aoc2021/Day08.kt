package it.toporowicz.aoc2021

typealias Segments = Set<Char>

fun main() {
    val notesEntries = InputReader.readInputData("it/toporowicz/aoc2021/day08.txt")
        .let { Day08InputDataParser().parse(it) }

    println("Part 1 result: ${Day08().countEasyDigits(notesEntries)}")
    println("Part 2 result: ${Day08().addAllOutputValuesFrom(notesEntries)}")
}

class Day08InputDataParser {
    fun parse(lines: List<String>): List<Day08.NotesEntry> {
        return lines
            .map { line ->
                line.split(" ")
                    .let {
                        Day08.NotesEntry(
                            uniqueSignalPatterns = it.subList(0, 10).map { segment -> segment.toCharArray().toSet() },
                            outputValues = it.subList(11, 15).map { segment -> segment.toCharArray().toSet() }
                        )
                    }
            }
    }
}

class Day08 {
    private val lengthsOfEasyNumbers = setOf(2, 3, 4, 7)

    fun countEasyDigits(notesEntries: List<NotesEntry>): Int {
        return notesEntries
            .flatMap { it.outputValues }
            .count { lengthsOfEasyNumbers.contains(it.size) }
    }

    fun addAllOutputValuesFrom(notesEntries: List<NotesEntry>): Int {
        return notesEntries.sumOf { decodeValue(it) }
    }

    private fun decodeValue(notesEntry: NotesEntry): Int {
        val (uniqueSignalPatterns, outputValues) = notesEntry

        val patternFor1 = getSegmentsWithLettersOfCountEqualTo(2, uniqueSignalPatterns)
        val patternFor4 = getSegmentsWithLettersOfCountEqualTo(4, uniqueSignalPatterns)
        val patternFor7 = getSegmentsWithLettersOfCountEqualTo(3, uniqueSignalPatterns)
        val patternFor8 = getSegmentsWithLettersOfCountEqualTo(7, uniqueSignalPatterns)

        val lettersForEAndG = patternFor8
            .minus(patternFor7)
            .minus(patternFor4)
            .minus(patternFor1)

        val candidatePatternsFor9 = uniqueSignalPatterns.filter { it.size == 6 }

        val letterForEAndGFrequencyInCandidatesPatternsFor9 = lettersForEAndG
            .associateBy { numOfOccurrencesIn(candidatePatternsFor9, it) }

        val letterForE = letterForEAndGFrequencyInCandidatesPatternsFor9[2]!!

        val patternFor9 = uniqueSignalPatterns
            .filter { it.size == 6 && !it.contains(letterForE) }
            .also { require(it.size == 1) }
            .first()

        val patternFor2 = uniqueSignalPatterns
            .filter { it.size == 5 && it.contains(letterForE) }
            .also { require(it.size == 1) }
            .first()

        val letterForF = patternFor1.minus(patternFor2).also { require(it.size == 1) }.first()
        val letterForC = patternFor1.minus(letterForF).also { require(it.size == 1) }.first()

        val patternFor6 = uniqueSignalPatterns
            .filter { it.size == 6 && !it.contains(letterForC) }
            .also { require(it.size == 1) }
            .first()

        val patternFor5 = uniqueSignalPatterns
            .filter { it.size == 5 && !it.contains(letterForC) }
            .also { require(it.size == 1) }
            .first()

        val patternFor0 = uniqueSignalPatterns
            .filter { it.size == 6 && it.contains(letterForC) && it.contains(letterForE) }
            .also { require(it.size == 1) }
            .first()

        val patternFor3 = uniqueSignalPatterns
            .filter { it.size == 5 && it != patternFor5 && !it.contains(letterForE) }
            .also { require(it.size == 1) }
            .first()

        val numberByPattern = mapOf(
            patternFor0 to 0,
            patternFor1 to 1,
            patternFor2 to 2,
            patternFor3 to 3,
            patternFor4 to 4,
            patternFor5 to 5,
            patternFor6 to 6,
            patternFor7 to 7,
            patternFor8 to 8,
            patternFor9 to 9,
        )

        require(numberByPattern.keys.toSet().size == 10)

        return outputValues
            .joinToString(separator = "") {
                val number = numberByPattern[it]
                number.toString()
            }
            .let { Integer.parseInt(it) }
    }

    private fun numOfOccurrencesIn(candidatePatternsFor9: List<Segments>, letter: Char) =
        candidatePatternsFor9.count { it.contains(letter) }

    private fun getSegmentsWithLettersOfCountEqualTo(count: Int, allSegments: List<Segments>) =
        allSegments
            .filter { it.size == count }
            .also { require(it.size == 1) }
            .first()

    data class NotesEntry(val uniqueSignalPatterns: List<Segments>, val outputValues: List<Segments>) {
        init {
            require(uniqueSignalPatterns.size == 10)
            require(outputValues.size == 4)
        }
    }

}