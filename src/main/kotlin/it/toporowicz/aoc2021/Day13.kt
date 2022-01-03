package it.toporowicz.aoc2021

fun main() {
    val inputData = InputReader.readInputData("it/toporowicz/aoc2021/day13.txt").let {
        Day13InputDataParser().parse(it)
    }

    println("Part 1 result: ${Day13().countDotsAfterFirstFoldFor(inputData)}")
    println("Part 2 result: \n${Day13().foldAndPrint(inputData)}")
}

class Day13InputDataParser {
    fun parse(lines: List<String>): Day13.InputData {
        val indexOfBlankLine = lines.indexOfFirst { it.isBlank() }

        val transparentPaperDescriptionLines = lines.take(indexOfBlankLine)
        val foldInstructionsLines = lines.drop(indexOfBlankLine + 1)

        val transparentPaper =
            transparentPaperDescriptionLines
                .map { rawCoordinates ->
                    rawCoordinates
                        .split(",")
                        .let {
                            Day13.Coordinate(it[0].toInt(), it[1].toInt())
                        }
                }
                .let { Day13.TransparentPaper(it.toSet()) }

        val foldInstructions = foldInstructionsLines.map { instructionLine ->
            instructionLine
                .replace("fold along ", "")
                .split("=")
                .let { if (it[0] == "x") Day13.FoldX(it[1].toInt()) else Day13.FoldY(it[1].toInt()) }
        }

        return Day13.InputData(transparentPaper, foldInstructions)
    }
}

class Day13 {
    fun countDotsAfterFirstFoldFor(inputData: InputData): Int {
        val (paper, foldInstructions) = inputData

        return paper
            .fold(foldInstructions.first())
            .countDots()
    }

    fun foldAndPrint(inputData: InputData): String {
        val (paper, foldInstructions) = inputData

        return foldInstructions
            .fold(paper) { accPaper, instruction -> accPaper.fold(instruction) }
            .print()
    }

    data class InputData(val paper: TransparentPaper, val foldInstructions: List<FoldInstruction>)

    data class Coordinate(val x: Int, val y: Int)

    interface FoldInstruction
    data class FoldY(val y: Int) : FoldInstruction
    data class FoldX(val x: Int) : FoldInstruction

    data class TransparentPaper(val dots: Set<Coordinate>) {
        fun print(): String {
            val maxX = dots.maxOf { it.x }
            val maxY = dots.maxOf { it.y }

            val printedPaper = StringBuffer()

            (0..maxY).forEach { y ->
                (0..maxX).forEach { x ->
                    if (dots.contains(Coordinate(x, y))) {
                        printedPaper.append("#")
                    } else {
                        printedPaper.append(".")
                    }

                }
                printedPaper.append("\n")
            }

            return printedPaper.toString()
        }

        fun fold(foldInstruction: FoldInstruction): TransparentPaper {
            return when (foldInstruction) {
                is FoldY -> foldY(foldInstruction.y)
                is FoldX -> foldX(foldInstruction.x)
                else -> throw RuntimeException("Unknown instruction $foldInstruction")
            }
        }

        private fun foldX(x: Int): TransparentPaper {
            val allDotsRightOfX = dots.filter { it.x > x }.toSet()
            val allDotsLeftOfX = dots.minus(allDotsRightOfX).toSet()

            val newDotsForDotsRightOfX = allDotsRightOfX
                .map { dotCoordinate ->
                    val numOfColsRightOfX = dotCoordinate.x - x
                    val xOffset = -numOfColsRightOfX

                    Coordinate(x + xOffset, dotCoordinate.y)
                }

            return TransparentPaper(allDotsLeftOfX.plus(newDotsForDotsRightOfX))
        }

        private fun foldY(y: Int): TransparentPaper {
            val allDotsBelowY = dots.filter { it.y > y }.toSet()
            val allDotsAboveY = dots.minus(allDotsBelowY).toSet()

            val newDotsForDotsBelowY = allDotsBelowY
                .map { dotCoordinate ->
                    val numOfRowsBelowY = dotCoordinate.y - y
                    val yOffset = -numOfRowsBelowY

                    Coordinate(dotCoordinate.x, y + yOffset)
                }

            return TransparentPaper(allDotsAboveY.plus(newDotsForDotsBelowY))
        }

        fun countDots() = dots.size
    }
}