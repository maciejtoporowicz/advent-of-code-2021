package it.toporowicz.aoc2021

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day08Test {
    private val expectedNotes = listOf(
        notesEntryFrom(
            listOf(
                "be",
                "cfbegad",
                "cbdgef",
                "fgaecd",
                "cgeb",
                "fdcge",
                "agebfd",
                "fecdb",
                "fabcd",
                "edb"
            ), listOf("fdgacbe", "cefdb", "cefbgd", "gcbe")
        ),
        notesEntryFrom(
            listOf(
                "edbfga",
                "begcd",
                "cbg",
                "gc",
                "gcadebf",
                "fbgde",
                "acbgfd",
                "abcde",
                "gfcbed",
                "gfec"
            ), listOf("fcgedb", "cgb", "dgebacf", "gc")
        ),
        notesEntryFrom(
            listOf(
                "fgaebd",
                "cg",
                "bdaec",
                "gdafb",
                "agbcfd",
                "gdcbef",
                "bgcad",
                "gfac",
                "gcb",
                "cdgabef"
            ), listOf("cg", "cg", "fdcagb", "cbg")
        ),
        notesEntryFrom(
            listOf(
                "fbegcd",
                "cbd",
                "adcefb",
                "dageb",
                "afcb",
                "bc",
                "aefdc",
                "ecdab",
                "fgdeca",
                "fcdbega"
            ), listOf("efabcd", "cedba", "gadfec", "cb")
        ),
        notesEntryFrom(
            listOf(
                "aecbfdg",
                "fbg",
                "gf",
                "bafeg",
                "dbefa",
                "fcge",
                "gcbea",
                "fcaegb",
                "dgceab",
                "fcbdga"
            ), listOf("gecf", "egdcabf", "bgf", "bfgea")
        ),
        notesEntryFrom(
            listOf(
                "fgeab",
                "ca",
                "afcebg",
                "bdacfeg",
                "cfaedg",
                "gcfdb",
                "baec",
                "bfadeg",
                "bafgc",
                "acf"
            ), listOf("gebdcfa", "ecba", "ca", "fadegcb")
        ),
        notesEntryFrom(
            listOf(
                "dbcfg",
                "fgd",
                "bdegcaf",
                "fgec",
                "aegbdf",
                "ecdfab",
                "fbedc",
                "dacgb",
                "gdcebf",
                "gf"
            ), listOf("cefg", "dcbef", "fcge", "gbcadfe")
        ),
        notesEntryFrom(
            listOf(
                "bdfegc",
                "cbegaf",
                "gecbf",
                "dfcage",
                "bdacg",
                "ed",
                "bedf",
                "ced",
                "adcbefg",
                "gebcd"
            ), listOf("ed", "bcgafe", "cdgba", "cbgef")
        ),
        notesEntryFrom(
            listOf(
                "egadfb",
                "cdbfeg",
                "cegd",
                "fecab",
                "cgb",
                "gbdefca",
                "cg",
                "fgcdab",
                "egfdb",
                "bfceg"
            ), listOf("gbdfcae", "bgc", "cg", "cgb")
        ),
        notesEntryFrom(
            listOf(
                "gcafb",
                "gcf",
                "dcaebfg",
                "ecagb",
                "gf",
                "abcdeg",
                "gaef",
                "cafbge",
                "fdbac",
                "fegbdc"
            ), listOf("fgae", "cfgab", "fg", "bagce")
        ),
    )

    @Test
    fun shouldParseInput() {
        // given
        val inputLines = InputReader.readInputData("it/toporowicz/aoc2021/day08.txt", Day08Test::class.java)

        // when
        val actualNotes = Day08InputDataParser().parse(inputLines)

        // then
        assertEquals(expectedNotes, actualNotes)
    }

    @Test
    fun shouldCountForPart1() {
        // when
        val actualCount = Day08().countEasyDigits(expectedNotes)

        // then
        assertEquals(26, actualCount)
    }

    @Test
    fun shouldCountForPart2() {
        // when
        val actualCount = Day08().addAllOutputValuesFrom(expectedNotes)

        // then
        assertEquals(61229, actualCount)
    }

    companion object {
        fun notesEntryFrom(uniqueSignals: List<String>, outputValues: List<String>) = Day08.NotesEntry(
            uniqueSignals.map { asSetOfChar(it) },
            outputValues.map { asSetOfChar(it) }
        )

        private fun asSetOfChar(string: String) = string.toCharArray().toSet()
    }
}