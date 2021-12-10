package it.toporowicz.aoc2021

fun main() {
    val inputInstructions = InputReader.readInputData("day03.txt")

    println("Part 1 result: ${Day03().part01(inputInstructions)}")
    println("Part 2 result: ${Day03().part02(inputInstructions)}")
}

class Day03 {
    fun part01(diagnosticReport: List<String>) =
        countBitsPerReportColumn(diagnosticReport)
            .let { decimalValueOfMostCommonBits(it) * decimalValueOfLeastCommonBits(it) }

    fun part02(diagnosticReport: List<String>): Int {
        val oxygenGeneratorRating = findOxygenGeneratorRating(diagnosticReport)
        val co2ScrubberRating = findCO2ScrubberRating(diagnosticReport)

        return oxygenGeneratorRating * co2ScrubberRating
    }

    private fun findCO2ScrubberRating(diagnosticReport: List<String>) = findRating(
        remainingDiagnosticReportLines = diagnosticReport,
        currentColumnIndex = 0,
        strategyToSelectBitToRetain = { columnIndex, countedBitsPerColumn ->
            countedBitsPerColumn.counterFor(columnIndex).let {
                if (it.bitsEqual()) 0 else it.leastCommonBit()
            }
        }
    )

    private fun findOxygenGeneratorRating(diagnosticReport: List<String>) = findRating(
        remainingDiagnosticReportLines = diagnosticReport,
        currentColumnIndex = 0,
        strategyToSelectBitToRetain = { columnIndex, countedBitsPerColumn ->
            countedBitsPerColumn.counterFor(columnIndex).let {
                if (it.bitsEqual()) 1 else it.mostCommonBit()
            }
        }
    )

    private fun decimalValueOfMostCommonBits(countedBitsPerColumn: CountedBitsPerColumn) =
        countedBitsPerColumn
            .fold("") { resultingBinaryValueAsString, counter -> resultingBinaryValueAsString + counter.mostCommonBit() }
            .let { Integer.parseInt(it, 2) }

    private fun decimalValueOfLeastCommonBits(countedBitsPerColumn: CountedBitsPerColumn) =
        countedBitsPerColumn
            .fold("") { resultingBinaryValueAsString, counter -> resultingBinaryValueAsString + counter.leastCommonBit() }
            .let { Integer.parseInt(it, 2) }

    private fun findRating(
        remainingDiagnosticReportLines: List<String>,
        currentColumnIndex: Int,
        strategyToSelectBitToRetain: (columnIndex: Int, countedBitsPerColumn: CountedBitsPerColumn) -> Int
    ): Int {
        if (remainingDiagnosticReportLines.size == 1) {
            return remainingDiagnosticReportLines.first().let { Integer.parseInt(it, 2) }
        }

        val bitToRetainAtCurrentColumnIndex = countBitsPerReportColumn(remainingDiagnosticReportLines)
            .let { countedBitsPerColumn -> strategyToSelectBitToRetain(currentColumnIndex, countedBitsPerColumn) }
            .toString()

        return findRating(
            remainingDiagnosticReportLines.filter { bits -> bits[currentColumnIndex].toString() != bitToRetainAtCurrentColumnIndex },
            currentColumnIndex + 1,
            strategyToSelectBitToRetain
        )
    }

    companion object Calculator {
        fun countBitsPerReportColumn(diagnosticReport: List<String>): CountedBitsPerColumn {
            return diagnosticReport
                .fold(CountedBitsPerColumn.empty()) { countedBitsForAllLines, reportLine ->
                    reportLine.foldIndexed(
                        countedBitsForAllLines
                    ) { characterIndex, countedBitsForLine, character ->
                        when (character) {
                            '0' -> countedBitsForLine.incrementZeroesFor(characterIndex)
                            '1' -> countedBitsForLine.incrementOnesFor(characterIndex)
                            else -> throw RuntimeException("Unexpected character '$character'")
                        }
                    }
                }
        }
    }

    data class CountedBitsPerColumn(private val bitCounterByPosition: Map<Int, BitCounter>) : Iterable<BitCounter> {
        fun incrementZeroesFor(columnIndex: Int) =
            CountedBitsPerColumn(
                bitCounterByPosition.plus(
                    columnIndex to
                            bitCounterByPosition
                                .getOrDefault(columnIndex, BitCounter.empty())
                                .incrementZeroes()
                )
            )

        fun incrementOnesFor(columnIndex: Int) =
            CountedBitsPerColumn(
                bitCounterByPosition.plus(
                    columnIndex to
                            bitCounterByPosition
                                .getOrDefault(columnIndex, BitCounter.empty())
                                .incrementOnes()
                )
            )

        fun counterFor(columnIndex: Int) =
            bitCounterByPosition.getOrElse(columnIndex) { throw RuntimeException("No such column $columnIndex") }

        override fun iterator(): Iterator<BitCounter> {
            return bitCounterByPosition.values.iterator()
        }

        companion object {
            fun empty() = CountedBitsPerColumn(emptyMap())
        }
    }
}

data class BitCounter(val zeroes: Int, val ones: Int) {
    fun incrementZeroes() = copy(zeroes = this.zeroes + 1)
    fun incrementOnes() = copy(ones = this.ones + 1)

    fun bitsEqual() = zeroes == ones

    fun mostCommonBit(): Int {
        if (zeroes == ones) {
            throw RuntimeException("Zeroes are equal to ones")
        }

        return if (zeroes > ones) 0 else 1
    }

    fun leastCommonBit(): Int {
        if (zeroes == ones) {
            throw RuntimeException("Zeroes are equal to ones")
        }

        return if (zeroes < ones) 0 else 1
    }

    companion object {
        fun empty() = BitCounter(0, 0)
    }
}