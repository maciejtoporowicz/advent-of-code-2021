package it.toporowicz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class Day12Test {
    @Test
    fun shouldReadCaveSystem() {
        // when
        val actualCaveSystem = Day12InputDataParser().parse(
            listOf(
                "start-A",
                "start-b",
                "A-c",
                "A-b",
                "b-d",
                "A-end",
                "b-end"
            )
        )

        // then
        val expectedCaveSystem = Day12.CaveSystem(
            setOf(
                Day12.Path(Day12.Start, Day12.Big("A")),
                Day12.Path(Day12.Big("A"), Day12.Start),
                Day12.Path(Day12.Start, Day12.Small("b")),
                Day12.Path(Day12.Small("b"), Day12.Start),
                Day12.Path(Day12.Big("A"), Day12.Small("c")),
                Day12.Path(Day12.Small("c"), Day12.Big("A")),
                Day12.Path(Day12.Big("A"), Day12.Small("b")),
                Day12.Path(Day12.Small("b"), Day12.Big("A")),
                Day12.Path(Day12.Small("b"), Day12.Small("d")),
                Day12.Path(Day12.Small("d"), Day12.Small("b")),
                Day12.Path(Day12.Big("A"), Day12.End),
                Day12.Path(Day12.End, Day12.Big("A")),
                Day12.Path(Day12.Small("b"), Day12.End),
                Day12.Path(Day12.End, Day12.Small("b"))
            )
        )
        assertEquals(expectedCaveSystem, actualCaveSystem)
    }

    @ParameterizedTest
    @MethodSource("pathCountingWithSingleVisitToSmallCaveTestData")
    fun shouldCountUniquePathsWhichGoThroughSmallCaveAtMostOnce(caveSystem: Day12.CaveSystem, expectedNumOfPaths: Int) {
        // when
        val actualNumOfPaths = Day12().countUniquePathsWhichGoThroughSmallCaveAtMostOnce(caveSystem)

        // then
        assertEquals(expectedNumOfPaths, actualNumOfPaths)
    }

    @ParameterizedTest
    @MethodSource("pathCountingWithAtMostTwoVisitsToSpecificSmallCaveTestData")
    fun shouldCountUniquePathsWhichGoThroughOneSmallCaveAtMostTwice(
        caveSystem: Day12.CaveSystem,
        expectedNumOfPaths: Int
    ) {
        // when
        val actualNumOfPaths = Day12().countUniquePathsWhichGoThroughOneSmallCaveAtMostTwice(caveSystem)

        // then
        assertEquals(expectedNumOfPaths, actualNumOfPaths)
    }

    companion object {
        private val caveSystem_1 = Day12InputDataParser().parse(
            listOf(
                "start-A",
                "start-b",
                "A-c",
                "A-b",
                "b-d",
                "A-end",
                "b-end"
            )
        )

        private val caveSystem_2 = Day12InputDataParser().parse(
            listOf(
                "dc-end",
                "HN-start",
                "start-kj",
                "dc-start",
                "dc-HN",
                "LN-dc",
                "HN-end",
                "kj-sa",
                "kj-HN",
                "kj-dc"
            ),
        )

        private val caveSystem_3 = Day12InputDataParser().parse(
            listOf(
                "fs-end",
                "he-DX",
                "fs-he",
                "start-DX",
                "pj-DX",
                "end-zg",
                "zg-sl",
                "zg-pj",
                "pj-he",
                "RW-he",
                "fs-DX",
                "pj-RW",
                "zg-RW",
                "start-pj",
                "he-WI",
                "zg-he",
                "pj-fs",
                "start-RW"
            )
        )

        @JvmStatic
        fun pathCountingWithSingleVisitToSmallCaveTestData(): Stream<Arguments> = Stream.of(
            Arguments.of(caveSystem_1, 10),
            Arguments.of(caveSystem_2, 19),
            Arguments.of(caveSystem_3, 226)
        )

        @JvmStatic
        fun pathCountingWithAtMostTwoVisitsToSpecificSmallCaveTestData(): Stream<Arguments> = Stream.of(
            Arguments.of(caveSystem_1, 36),
            Arguments.of(caveSystem_2, 103),
            Arguments.of(caveSystem_3, 3509)
        )
    }
}