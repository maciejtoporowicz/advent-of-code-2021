package it.toporowicz.aoc2021

fun main() {
    val heightMap = InputReader.readInputData("it/toporowicz/aoc2021/day09.txt")
        .let { Day09InputDataParser().parse(it) }

    println("Part 1 result: ${Day09().sumRiskLevelOfLowPoints(heightMap)}")
    println("Part 2 result: ${Day09().multiplySizesOfThreeBiggestBasins(heightMap)}")
}

class Day09InputDataParser {
    fun parse(lines: List<String>): HeightMap =
        lines
            .map { line ->
                line.map { Integer.parseInt(it.toString()) }
            }
            .let { HeightMap(it) }
}

class Day09 {
    fun sumRiskLevelOfLowPoints(heightMap: HeightMap): Int =
        heightMap
            .findLowPoints()
            .sumOf { it.riskLevel() }

    fun multiplySizesOfThreeBiggestBasins(heightMap: HeightMap): Int =
        heightMap
            .findBiggestBasins(3)
            .map { it.size }
            .reduce { a, b -> a * b }
}

data class HeightMap(private val heights: List<List<Int>>) {
    val maxX: Int = heights.first().size - 1
    val maxY: Int = heights.size - 1

    private val locations: Map<Coordinate, Location>

    init {
        locations =
            (0..maxY).flatMap { y ->
                (0..maxX).map { x ->
                    val coordinate = Coordinate(x, y)

                    val height = heights[y][x]

                    val adjacentLocations = setOf<AdjacentLocation>()
                        .let { if (y > 0) it.plus(AdjacentLocation(heights[y - 1][x], Coordinate(x, y - 1))) else it }
                        .let {
                            if (x < maxX) it.plus(
                                AdjacentLocation(
                                    heights[y][x + 1],
                                    Coordinate(x + 1, y)
                                )
                            ) else it
                        }
                        .let {
                            if (y < maxY) it.plus(
                                AdjacentLocation(
                                    heights[y + 1][x],
                                    Coordinate(x, y + 1)
                                )
                            ) else it
                        }
                        .let { if (x > 0) it.plus(AdjacentLocation(heights[y][x - 1], Coordinate(x - 1, y))) else it }

                    Location(
                        height = height,
                        adjacentLocations = adjacentLocations,
                        coordinate = coordinate
                    )
                }
            }.associateBy { it.coordinate }
    }

    /*
    The height map uses the following coordinate scheme:
    a (x=0, y=0)
        ◉-----------▶︎ x
        |
        |       ◉ b(x=5, y=4)
        |
        |    ◉ c(x=3, y=7)
        ▼
        y
    */
    fun locationAt(coordinate: Coordinate): Location = locations[coordinate]!!

    fun findLowPoints(): List<Location> =
        locations.values
            .filter { it.isLowestAmongAdjacentLocations() }
            .toList()

    fun findBiggestBasins(howMany: Int): List<Set<Location>> =
        findLowPoints()
            .map { gatherBiggerAdjacentLocationsBelongingToSameBasin(it) }
            .sortedByDescending { it.size }
            .take(howMany)

    private fun gatherBiggerAdjacentLocationsBelongingToSameBasin(location: Location): Set<Location> {
        return location
            .adjacentLocations
            .map { locationAt(it.coordinate) }
            .filter { it.height < 9 && it.height > location.height }
            .flatMap { setOf(location).plus(it).plus(gatherBiggerAdjacentLocationsBelongingToSameBasin(it)) }
            .toSet()
    }
}

data class AdjacentLocation(
    val height: Int,
    val coordinate: Coordinate
)

data class Location(
    val height: Int,
    val adjacentLocations: Set<AdjacentLocation>,
    val coordinate: Coordinate
) {
    fun isLowestAmongAdjacentLocations() =
        adjacentLocations.all { height < it.height }

    fun riskLevel() = 1 + height
}

data class Coordinate(val x: Int, val y: Int)
