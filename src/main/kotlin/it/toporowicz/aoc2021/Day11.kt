package it.toporowicz.aoc2021

fun main() {
    val octopusMatrix = InputReader.readInputData("it/toporowicz/aoc2021/day11.txt").let {
        Day11InputDataParser().parse(it)
    }

    println("Part 1 result: ${Day11().countFlashes(octopusMatrix, 100)}")
}

class Day11InputDataParser {
    fun parse(inputLines: List<String>) =
        inputLines.map {
            it.toCharArray().map { character -> Integer.parseInt(character.toString()) }
        }.let { octopusEnergies ->
            require(octopusEnergies.size == octopusEnergies.first().size) { "rows x cols need to be equal" }

            val octopusesByCoordinate: Map<Day11.Coordinate, Day11.Octopus> = octopusEnergies
                .flatMapIndexed { rowIndex, row ->
                    row.mapIndexed { colIndex, energy ->
                        val coordinate = Day11.Coordinate(rowIndex, colIndex)

                        coordinate to Day11.Octopus(energy, 0)
                    }
                }.toMap()

            Day11.OctopusMatrix(octopusesByCoordinate)
        }
}

class Day11 {
    fun countFlashes(octopusMatrix: OctopusMatrix, howManySteps: Int) =
        octopusMatrix
            .step(howManySteps)
            .countFlashes()

    data class OctopusMatrix(private val octopusesByCoordinate: Map<Coordinate, Octopus>) {
        fun step(howMany: Int): OctopusMatrix {
            return (1..howMany).fold(this) { accMatrix, stepNum ->
                accMatrix.step()
            }
        }

        fun countFlashes() = octopusesByCoordinate.values.sumOf { it.flashCount }

        private fun step(): OctopusMatrix {
            val octopusWithInitiallyIncrementedEnergy = octopusesByCoordinate.mapValues { (_, octopus) ->
                octopus.copy(energy = octopus.energy + 1)
            }

            val octopusWithEnergyIncrementFinished = flash(octopusWithInitiallyIncrementedEnergy, emptySet())

            return copy(octopusesByCoordinate = octopusWithEnergyIncrementFinished)
        }

        private fun flash(
            octopusesByCoordinate: Map<Coordinate, Octopus>,
            coordinatesOfOctopusWhichAlreadyFlashedInThisStep: Set<Coordinate>
        ): Map<Coordinate, Octopus> {
            val coordinatesOfOctopusWhichNeedToFlashNow = octopusesByCoordinate
                .filter { (_, octopus) -> octopus.energy > 9 }
                .keys

            if (coordinatesOfOctopusWhichNeedToFlashNow.isEmpty()) {
                return octopusesByCoordinate
            }

            val afterFlashing = coordinatesOfOctopusWhichNeedToFlashNow
                .fold(octopusesByCoordinate) { accOctopusesByCoordinate, coordinatesOfOctopusWhichNeedsToFlashNow ->
                    val adjacentCoordinates = coordinatesOfOctopusWhichNeedsToFlashNow.adjacentCoordinates()

                    accOctopusesByCoordinate.mapValues { (partialCoordinate, partialOctopus) ->
                        if (coordinatesOfOctopusWhichAlreadyFlashedInThisStep.contains(partialCoordinate)) {
                            return@mapValues partialOctopus
                        }
                        if (coordinatesOfOctopusWhichNeedToFlashNow.contains(partialCoordinate) && partialCoordinate != coordinatesOfOctopusWhichNeedsToFlashNow) {
                            return@mapValues partialOctopus
                        }
                        if (partialCoordinate == coordinatesOfOctopusWhichNeedsToFlashNow) {
                            return@mapValues partialOctopus.copy(energy = 0, flashCount = partialOctopus.flashCount + 1)
                        }
                        if (adjacentCoordinates.contains(partialCoordinate)) {
                            return@mapValues partialOctopus.copy(energy = partialOctopus.energy + 1)
                        }

                        return@mapValues partialOctopus
                    }
                }

            return flash(
                afterFlashing,
                coordinatesOfOctopusWhichAlreadyFlashedInThisStep.plus(coordinatesOfOctopusWhichNeedToFlashNow)
            )
        }
    }

    data class Octopus(val energy: Int, val flashCount: Long)

    data class Coordinate(val row: Int, val col: Int) {
        fun adjacentCoordinates() = setOf(up(), upRight(), right(), downRight(), down(), downLeft(), left(), upLeft())

        private fun up() = copy(row = row - 1)
        private fun upRight() = copy(row = row - 1, col = col + 1)
        private fun right() = copy(col = col + 1)
        private fun downRight() = copy(row = row + 1, col = col + 1)
        private fun down() = copy(row = row + 1)
        private fun downLeft() = copy(row = row + 1, col = col - 1)
        private fun left() = copy(col = col - 1)
        private fun upLeft() = copy(row = row - 1, col = col - 1)
    }
}