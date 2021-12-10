package it.toporowicz.aoc2021

fun main() {
    val inputInstructions = InputReader.readInputData("day02.txt")

    println("Part 1 result: ${Day02().part01(inputInstructions)}")
    println("Part 2 result: ${Day02().part02(inputInstructions)}")
}

class Day02 {

    fun part01(instructions: List<String>): Int {
        val submarine =
            instructions
                .fold(
                    SubmarinePart01.create()
                ) { submarine, instruction -> submarine.read(InstructionParser.parse(instruction)) }

        return submarine.horizontalPosition * submarine.depth
    }

    fun part02(instructions: List<String>): Int {
        val submarine =
            instructions
                .fold(
                    SubmarinePart02.create()
                ) { submarine, instruction -> submarine.read(InstructionParser.parse(instruction)) }

        return submarine.horizontalPosition * submarine.depth
    }

    data class SubmarinePart01(val horizontalPosition: Int, val depth: Int) {
        fun read(instruction: Instruction): SubmarinePart01 {
            return when (instruction) {
                is Forward -> this.forward(instruction.x)
                is Up -> this.up(instruction.x)
                is Down -> this.down(instruction.x)
                else -> throw RuntimeException("Unknown instruction ${instruction.javaClass}}")
            }
        }

        private fun forward(x: Int): SubmarinePart01 = copy(horizontalPosition = this.horizontalPosition + x)
        private fun up(x: Int): SubmarinePart01 = copy(depth = this.depth - x)
        private fun down(x: Int): SubmarinePart01 = copy(depth = this.depth + x)

        companion object Factory {
            fun create() = SubmarinePart01(0, 0)
        }
    }

    data class SubmarinePart02(val horizontalPosition: Int, val depth: Int, val aim: Int) {
        fun read(instruction: Instruction): SubmarinePart02 {
            return when (instruction) {
                is Forward -> this.forward(instruction.x)
                is Up -> this.up(instruction.x)
                is Down -> this.down(instruction.x)
                else -> throw RuntimeException("Unknown instruction ${instruction.javaClass}}")
            }
        }

        private fun forward(x: Int): SubmarinePart02 = copy(
            horizontalPosition = this.horizontalPosition + x,
            depth = this.depth + (this.aim * x)
        )

        private fun up(x: Int): SubmarinePart02 = copy(aim = this.aim - x)
        private fun down(x: Int): SubmarinePart02 = copy(aim = this.aim + x)

        companion object Factory {
            fun create() = SubmarinePart02(0, 0, 0)
        }
    }

    interface Instruction {
        val x: Int
    }

    data class Forward(override val x: Int) : Instruction
    data class Up(override val x: Int) : Instruction
    data class Down(override val x: Int) : Instruction

    object InstructionParser {
        fun parse(instruction: String): Instruction {
            val (instructionLiteral, x) =
                instruction
                    .split(" ")
                    .let { Pair(it[0], Integer.parseInt(it[1])) }

            return when (instructionLiteral) {
                "forward" -> Forward(x)
                "up" -> Up(x)
                "down" -> Down(x)
                else -> throw RuntimeException()
            }
        }
    }
}