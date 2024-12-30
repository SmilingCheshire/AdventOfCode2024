import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd7"

    // Read the input file
    val inputLines = File(inputFileName).readLines()

    // Function to evaluate possible results by placing operators
    fun evaluateOperators(numbers: List<Long>, target: Long): Boolean {
        val ops = listOf('+', '*')

        fun evaluate(expression: List<Long>, operators: List<Char>): Long {
            var result = expression[0]
            for (i in operators.indices) {
                when (operators[i]) {
                    '+' -> result += expression[i + 1]
                    '*' -> result *= expression[i + 1]
                }
            }
            return result
        }

        // Generate all combinations of operators
        fun generateOperatorCombinations(size: Int): List<List<Char>> {
            if (size == 0) return listOf(emptyList())
            val smaller = generateOperatorCombinations(size - 1)
            return smaller.flatMap { combo ->
                ops.map { combo + it }
            }
        }

        val operatorCombinations = generateOperatorCombinations(numbers.size - 1)
        return operatorCombinations.any { evaluate(numbers, it) == target }
    }

    // Process each line and calculate the result
    var totalCalibrationResult = 0L

    for (line in inputLines) {
        val parts = line.split(": ")
        val target = parts[0].toLong()
        val numbers = parts[1].split(" ").map { it.toLong() }

        if (evaluateOperators(numbers, target)) {
            totalCalibrationResult += target
        }
    }

    println("Total Calibration Result: $totalCalibrationResult")
}
