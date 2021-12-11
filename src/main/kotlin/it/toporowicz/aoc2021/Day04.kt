package it.toporowicz.aoc2021

fun main() {
    val inputData =
        InputReader
            .readInputData("it/toporowicz/aoc2021/day04.txt")
            .let { Day04BoardDataParser().parseFrom(it) }

    println("Part 1 result: ${Day04().part01(inputData.boards, inputData.numbers)}")
    println("Part 2 result: ${Day04().part02(inputData.boards, inputData.numbers)}")
}

class Day04BoardDataParser {
    fun parseFrom(inputDataLines: List<String>): Day04InputData {
        val numbers = inputDataLines.first().split(",").map { Integer.parseInt(it) }

        val boardData = mutableListOf<List<List<Int>>>()

        val rowsBeforeFirstBoard = 2
        val rowsPerBoard = 5
        val rowsBetweenBoardData = 1

        var remainingInputLinesWithBoardData = inputDataLines.drop(rowsBeforeFirstBoard)

        while (remainingInputLinesWithBoardData.isNotEmpty()) {
            remainingInputLinesWithBoardData
                .take(rowsPerBoard)
                .map { row -> row.split(" ").filter { it.isNotBlank() }.map { col -> Integer.parseInt(col) } }
                .let { boardData.add(it) }

            remainingInputLinesWithBoardData =
                remainingInputLinesWithBoardData.drop(rowsPerBoard + rowsBetweenBoardData)
        }

        return Day04InputData(boardData.map { Day04.Board.create(it) }, numbers)
    }

    data class Day04InputData(val boards: List<Day04.Board>, val numbers: List<Int>)
}

class Day04 {
    fun part01(boards: List<Board>, numbers: List<Int>): Int {
        val (winningBoard, winningNumber) = findWinner(boards, numbers)

        val sumOfUnmarkedNumbers =
            winningBoard.rows
                .flatMap { row -> row.filter { !it.isMarked } }
                .sumOf { it.number }

        return sumOfUnmarkedNumbers * winningNumber
    }

    fun part02(boards: List<Board>, numbers: List<Int>): Int {
        val (winningBoard, winningNumber) = findLastWinner(boards, numbers)

        val sumOfUnmarkedNumbers =
            winningBoard.rows
                .flatMap { row -> row.filter { !it.isMarked } }
                .sumOf { it.number }

        return sumOfUnmarkedNumbers * winningNumber
    }

    private fun findLastWinner(remainingBoards: List<Board>, remainingNumbers: List<Int>): BingoWinner {
        val currentWinner = findWinner(remainingBoards, remainingNumbers)

        if (currentWinner.boards.size == 1) {
            return currentWinner
        }

        val (_, _, boardsStateAfterCurrentWinner, numbersRemainingAfterCurrentWinner) = currentWinner

        val newRemainingNumbers = numbersRemainingAfterCurrentWinner.drop(1)
        val newRemainingBoards = boardsStateAfterCurrentWinner.filter { !it.isBingo() }

        return findLastWinner(newRemainingBoards, newRemainingNumbers)
    }

    private fun findWinner(boards: List<Board>, remainingNumbers: List<Int>): BingoWinner {
        require(boards.isNotEmpty()) { "boards are empty" }
        require(remainingNumbers.isNotEmpty()) { "numbers are empty" }

        val numberInThisRound = remainingNumbers.first()

        val boardsAfterThisRound = boards.map {
            it.mark(numberInThisRound)
        }

        return boardsAfterThisRound
            .firstOrNull { it.isBingo() }
            ?.let {
                BingoWinner(
                    winningBoard = it,
                    winningNumber = numberInThisRound,
                    boards = boardsAfterThisRound,
                    remainingNumbers = remainingNumbers
                )
            }
            ?: findWinner(boardsAfterThisRound, remainingNumbers.drop(1))
    }

    data class BingoWinner(
        val winningBoard: Board,
        val winningNumber: Int,
        val boards: List<Board>,
        val remainingNumbers: List<Int>
    )

    data class Board(val rows: List<List<Number>>) {
        fun mark(number: Int): Board {
            return Board(
                rows.map { row ->
                    row.map { col -> if (col.number == number) col.mark() else col }
                }
            )
        }

        fun isBingo(): Boolean {
            val rowsBingo = Array(rows.size) { true }
            val colsBingo = Array(rows.first().size) { true }

            rows.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, col ->
                    if (!col.isMarked) {
                        rowsBingo[rowIndex] = false
                        colsBingo[colIndex] = false
                    }
                }
            }

            return rowsBingo.any { it } || colsBingo.any { it }
        }

        companion object {
            fun create(rows: List<List<Int>>) =
                Board(
                    rows.map { row ->
                        row.map { col -> Number(number = col, isMarked = false) }
                    }
                )
        }

        data class Number(val number: Int, val isMarked: Boolean) {
            fun mark() = copy(isMarked = true)
        }
    }
}