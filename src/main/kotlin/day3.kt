import java.io.File
fun main() {
    // Path to the input file (modify as necessary)
    val inputFileName = "src/main/kotlin/inputd3"

    try {
        // Read input from the file
        val input = File(inputFileName).readText()

        // Extract valid mul instructions and compute the sum
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

        // Find all matches and calculate the sum of their results
        val sum = regex.findAll(input).sumOf { matchResult ->
            val (x, y) = matchResult.destructured
            x.toInt() * y.toInt()
        }

        // Output the sum
        println("Sum of all valid mul results: $sum")
    } finally {

    }
}


