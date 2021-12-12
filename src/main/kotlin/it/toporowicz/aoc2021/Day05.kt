package it.toporowicz.aoc2021

import java.math.BigDecimal
import java.util.stream.IntStream

fun main() {
    val lines =
        InputReader.readInputData("it/toporowicz/aoc2021/day05.txt").let { Day05.Day05DataParser().parse(it) }

    println("Part 1 result: ${Day05().part01(lines)}")
    println("Part 2 result: ${Day05().part02(lines)}")
}

class Day05 {
    fun part01(lines: List<Line>): Int {
        val onlyVerticalOrHorizontalLines = lines
            .filter {
                val (a, b) = it
                a.x == b.x || a.y == b.y
            }

        val maxPoint = findMaxPointIn(onlyVerticalOrHorizontalLines)
        val initialMap = HydrothermalVentsMap.initWithZeroes(maxPoint)

        val drawnMap = onlyVerticalOrHorizontalLines.fold(initialMap) { currentMap, line -> currentMap.draw(line) }

        return (0..maxPoint.y).flatMap { x ->
            (0..maxPoint.x).map { y ->
                drawnMap.valueAt(Point(x, y))
            }
        }.count { it >= 2 }
    }

    fun part02(lines: List<Line>): Int {
        val maxPoint = findMaxPointIn(lines)
        val initialMap = HydrothermalVentsMap.initWithZeroes(maxPoint)

        val drawnMap = lines.fold(initialMap) { currentMap, line -> currentMap.draw(line) }

        return (0..maxPoint.y).flatMap { x ->
            (0..maxPoint.x).map { y ->
                drawnMap.valueAt(Point(x, y))
            }
        }.count { it >= 2 }
    }

    private fun findMaxPointIn(lines: List<Line>) =
        lines.fold(Point(0, 0)) { currentMax, line ->
            val newMaxX = IntStream.of(currentMax.x, line.a.x, line.b.x).max().orElseThrow()
            val newMaxY = IntStream.of(currentMax.y, line.a.y, line.b.y).max().orElseThrow()
            Point(newMaxX, newMaxY)
        }

    class Day05DataParser {
        fun parse(inputLines: List<String>) =
            inputLines
                .map { line ->
                    line.split(" ")
                        .let {
                            val pointA = readPoint(it.component1())
                            val pointB = readPoint(it.component3())
                            Line(pointA, pointB)
                        }
                }

        private fun readPoint(rawPoint: String) = rawPoint.split(",").let {
            Point(Integer.parseInt(it[0]), Integer.parseInt(it[1]))
        }
    }

    data class Line(val a: Point, val b: Point) {
        fun contains(coordinate: Point): Boolean {
            val x = BigDecimal(coordinate.x)
            val y = BigDecimal(coordinate.y)
            val x1 = BigDecimal(a.x)
            val x2 = BigDecimal(b.x)
            val y1 = BigDecimal(a.y)
            val y2 = BigDecimal(b.y)

            val minY = y1.min(y2)
            val maxY = y1.max(y2)
            val minX = x1.min(x2)
            val maxX = x1.max(x2)

            if (y < minY || y > maxY) {
                return false
            }
            if (x < minX || x > maxX) {
                return false
            }
            if (x1 == x2) {
                return true
            }

            // To check whether point belongs to a line:
            // y = ax + b
            // to calculate a and b:
            // y1 = ax1 + b
            // y2 = ax2 + b
            // which gives us
            // a = (y1 - y2) / (x1 - x2)
            // b can be calculated based on either points on the line
            // b = y1 - ax1
            //
            // a = fnA, b = fnB

            val fnA = (y1 - y2) / (x1 - x2)
            val fnB = y1 - (fnA * x1)

            return y == (fnA * x) + fnB
        }
    }

    data class Point(val x: Int, val y: Int)

    data class HydrothermalVentsMap(private val map: List<List<Int>>) {
        fun draw(line: Line) =
            copy(
                map = this.map.mapIndexed { mapY, row ->
                    row.mapIndexed { mapX, value ->
                        if (line.contains(Point(mapX, mapY))) value + 1 else value
                    }
                }
            )

        fun print() {
            map.forEach { row ->
                row.map { col -> if (col == 0) "." else col.toString() }.forEach { print(it) }
                println()
            }
            println()
        }

        fun valueAt(point: Point) = map[point.y][point.x]

        companion object {
            fun initWithZeroes(maxPoint: Point): HydrothermalVentsMap =
                HydrothermalVentsMap(
                    List(maxPoint.y + 1) { List(maxPoint.x + 1) { 0 } }
                )
        }
    }
}