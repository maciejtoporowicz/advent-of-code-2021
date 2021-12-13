package it.toporowicz.aoc2021

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class Day06Test {
    @Test
    fun shouldParseFishSchool() {
        // given
        val inputLines = InputReader.readInputData("it/toporowicz/aoc2021/day06.txt", Day06Test::class.java)

        // when
        val actualFishSchool = FishSchoolParser().parse(inputLines)

        // then
        val expectedFishSchool = day06ExampleFishSchool
        assertEquals(expectedFishSchool, actualFishSchool)
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun shouldCountNumberOfFishAfterSpecifiedDays(
        fishSchool: Day06.FishSchool,
        daysToForward: Int,
        expectedNumOfFish: Long
    ) {
        // when
        val actualNumOfFish = Day06().part01(fishSchool, daysToForward)

        // then
        assertEquals(expectedNumOfFish, actualNumOfFish)
    }

    companion object {
        private val day06ExampleFishSchool = Day06.FishSchool(
            listOf(
                Day06.Fish(3),
                Day06.Fish(4),
                Day06.Fish(3),
                Day06.Fish(1),
                Day06.Fish(2),
            )
        )

        @JvmStatic
        fun testCases() = listOf(
            Arguments.of(day06ExampleFishSchool, 0, 5L),
            Arguments.of(day06ExampleFishSchool, 18, 26L),
            Arguments.of(day06ExampleFishSchool, 80, 5934L)
        )
    }
}