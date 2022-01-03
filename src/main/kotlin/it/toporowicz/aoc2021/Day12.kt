package it.toporowicz.aoc2021

fun main() {
    val caveSystem = InputReader.readInputData("it/toporowicz/aoc2021/day12.txt").let {
        Day12InputDataParser().parse(it)
    }

    println("Part 1 result: ${Day12().countUniquePathsWhichGoThroughSmallCaveAtMostOnce(caveSystem)}")
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
    fun countUniquePathsWhichGoThroughSmallCaveAtMostOnce(caveSystem: CaveSystem) =
        caveSystem.getPathsFromStartToEnd().size

    data class CaveSystem(val paths: Set<Path>) {
        init {
            require(paths.any { path -> path.from == Start }) { "Must have a path starting at Start" }
            require(paths.any { path -> path.to == End }) { "Must have a path ending at End" }
        }

        private fun pathsFrom(cave: Cave) = paths.filter { path -> path.from == cave }

        fun getPathsFromStartToEnd(): List<List<Cave>> {
            return findPathToEnd(Start, listOf())
        }

        private fun findPathToEnd(currentCave: Cave, road: List<Cave>): List<List<Cave>> {
            if (currentCave == End) {
                return listOf(road.plus(End))
            }

            val cavesToCheckFromCurrentCave = pathsFrom(currentCave)
                .map { it.to }
                .filterNot { it is Start || (it is Small && road.contains(it)) }

            return cavesToCheckFromCurrentCave
                .flatMap { findPathToEnd(it, road.plus(currentCave)) }
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