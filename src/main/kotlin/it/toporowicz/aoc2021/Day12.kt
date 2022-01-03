package it.toporowicz.aoc2021

fun main() {
    val caveSystem = InputReader.readInputData("it/toporowicz/aoc2021/day12.txt").let {
        Day12InputDataParser().parse(it)
    }

    println("Part 1 result: ${Day12().countUniquePathsWhichGoThroughSmallCaveAtMostOnce(caveSystem)}")
    println("Part 2 result: ${Day12().countUniquePathsWhichGoThroughOneSmallCaveAtMostTwice(caveSystem)}")
}

class Day12InputDataParser {
    fun parse(lines: List<String>) =
        lines.flatMap { line ->
            line.split("-").let {
                val from = readCave(it[0])
                val to = readCave(it[1])
                listOf(
                    Day12.Path(from, to),
                    Day12.Path(to, from)
                )
            }
        }.toSet()
            .let { Day12.CaveSystem(it) }

    private fun readCave(name: String): Day12.Cave {
        return when (name) {
            "start" -> {
                Day12.Start
            }
            "end" -> {
                Day12.End
            }
            name.lowercase() -> {
                Day12.Small(name)
            }
            name.uppercase() -> {
                Day12.Big(name)
            }
            else -> {
                throw RuntimeException("Unknown type of cave for name: $name")
            }
        }
    }
}

class Day12 {
    fun countUniquePathsWhichGoThroughSmallCaveAtMostOnce(caveSystem: CaveSystem): Int {
        val visitLimit = caveSystem.paths
            .flatMap { listOf(it.from, it.to) }
            .toSet()
            .associateWith {
                when (it) {
                    is Start -> 1
                    is End -> 1
                    is Small -> 1
                    else -> null
                }
            }

        return caveSystem
            .getPathsFromStartToEnd(visitLimit)
            .size
    }

    fun countUniquePathsWhichGoThroughOneSmallCaveAtMostTwice(caveSystem: CaveSystem): Int {
        val allCaves = caveSystem.paths
            .flatMap { listOf(it.from, it.to) }
            .toSet()

        val smallCaves = allCaves.filterIsInstance<Small>()

        val allPossibleVisitLimits =
            (smallCaves.indices)
                .map { smallCaveIndex ->
                    allCaves.associateWith {
                        when (it) {
                            is Start -> 1
                            is End -> 1
                            is Small -> if (it == smallCaves[smallCaveIndex]) 2 else 1
                            else -> null
                        }
                    }
                }

        return allPossibleVisitLimits.flatMap { visitLimit ->
            caveSystem.getPathsFromStartToEnd(visitLimit)
        }.toSet().size
    }

    data class CaveSystem(val paths: Set<Path>) {
        init {
            require(paths.any { path -> path.from == Start }) { "Must have a path starting at Start" }
            require(paths.any { path -> path.to == End }) { "Must have a path ending at End" }
        }

        private fun pathsFrom(cave: Cave) = paths.filter { path -> path.from == cave }

        fun getPathsFromStartToEnd(visitLimitPerCave: Map<Cave, Int?>): List<List<Cave>> {
            return findPathToEnd(Start, listOf(), visitLimitPerCave)
        }

        private fun findPathToEnd(
            currentCave: Cave,
            road: List<Cave>,
            visitLimitPerCave: Map<Cave, Int?>
        ): List<List<Cave>> {
            if (currentCave == End) {
                return listOf(road.plus(End))
            }

            val cavesToCheckFromCurrentCave = pathsFrom(currentCave)
                .map { it.to }
                .filter { canVisit(it, road, visitLimitPerCave) }

            return cavesToCheckFromCurrentCave
                .flatMap { findPathToEnd(it, road.plus(currentCave), visitLimitPerCave) }
        }

        private fun canVisit(cave: Cave, road: List<Cave>, visitLimitPerCave: Map<Cave, Int?>): Boolean {
            val visitLimit = visitLimitPerCave[cave] ?: return true

            val visitCount = road.count { it == cave }

            return visitCount < visitLimit
        }
    }

    data class Path(val from: Cave, val to: Cave)

    interface Cave {
        val name: String
    }

    data class Small(override val name: String) : Cave {
        init {
            require(name.lowercase() == name) { "Needs to be lowercase" }
        }
    }

    data class Big(override val name: String) : Cave {
        init {
            require(name.uppercase() == name) { "Needs to be uppercase" }
        }
    }

    object Start : Cave {
        override val name: String
            get() = "start"
    }

    object End : Cave {
        override val name: String
            get() = "end"
    }
}