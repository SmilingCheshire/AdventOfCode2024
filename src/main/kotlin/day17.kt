import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("src/main/kotlin/inputd17").readLines()

    // Parse the registers
    val registerA = input[0].substringAfter("Register A: ").toInt()
    val registerB = input[1].substringAfter("Register B: ").toInt()
    val registerC = input[2].substringAfter("Register C: ").toInt()

    // Parse the program
    val program = input[4].substringAfter("Program: ").split(",").map { it.toInt() }

    // Run the program
    val output = runProgram(program, registerA, registerB, registerC)

    // Print the output
    println(output.joinToString(","))
}

fun runProgram(program: List<Int>, initialA: Int, initialB: Int, initialC: Int): List<Int> {
    var instructionPointer = 0
    var registerA = initialA
    var registerB = initialB
    var registerC = initialC

    val output = mutableListOf<Int>()

    while (instructionPointer < program.size) {
        val opcode = program[instructionPointer]
        val operand = if (instructionPointer + 1 < program.size) program[instructionPointer + 1] else 0

        when (opcode) {
            0 -> { // adv: A = A / 2^operand
                val divisor = 2.0.pow(getComboOperandValue(operand, registerA, registerB, registerC).toDouble()).toInt()
                registerA /= divisor
            }
            1 -> { // bxl: B = B XOR operand
                registerB = registerB xor operand
            }
            2 -> { // bst: B = operand % 8
                registerB = getComboOperandValue(operand, registerA, registerB, registerC) % 8
            }
            3 -> { // jnz: Jump if A != 0
                if (registerA != 0) {
                    instructionPointer = operand
                    continue
                }
            }
            4 -> { // bxc: B = B XOR C
                registerB = registerB xor registerC
            }
            5 -> { // out: Output operand % 8
                val value = getComboOperandValue(operand, registerA, registerB, registerC) % 8
                output.add(value)
            }
            6 -> { // bdv: B = A / 2^operand
                val divisor = 2.0.pow(getComboOperandValue(operand, registerA, registerB, registerC).toDouble()).toInt()
                registerB = registerA / divisor
            }
            7 -> { // cdv: C = A / 2^operand
                val divisor = 2.0.pow(getComboOperandValue(operand, registerA, registerB, registerC).toDouble()).toInt()
                registerC = registerA / divisor
            }
        }

        // Move to the next instruction
        instructionPointer += 2
    }

    return output
}

fun getComboOperandValue(operand: Int, registerA: Int, registerB: Int, registerC: Int): Int {
    return when (operand) {
        0, 1, 2, 3 -> operand // Literal values
        4 -> registerA
        5 -> registerB
        6 -> registerC
        else -> throw IllegalArgumentException("Invalid combo operand: $operand")
    }
}
