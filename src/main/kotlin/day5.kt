import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd5"

    try {
        // Read and split input lines
        val inputLines = File(inputFileName).readLines()

        // Separate rules and updates based on their format
        val rules = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()

        inputLines.forEach { line ->
            when {
                line.contains("|") -> {
                    val (x, y) = line.split("|").map { it.toInt() }
                    rules.add(x to y)
                }
                line.contains(",") -> {
                    updates.add(line.split(",").map { it.toInt() })
                }
            }
        }

        // Build the graph for the rules
        val graph = mutableMapOf<Int, MutableList<Int>>()
        rules.forEach { (x, y) ->
            graph.computeIfAbsent(x) { mutableListOf() }.add(y)
        }

        // Function to validate an update's order
        fun isValidOrder(update: List<Int>): Boolean {
            val indexMap = update.withIndex().associate { it.value to it.index }
            for ((x, y) in rules) {
                if (x in indexMap && y in indexMap && indexMap[x]!! > indexMap[y]!!) {
                    return false // Rule violated
                }
            }
            return true
        }

        // Process updates and compute the result
        val middlePages = updates.filter { isValidOrder(it) }
            .map { update -> update[update.size / 2] }

        val result = middlePages.sum()
        println("The sum of middle pages from correctly-ordered updates is: $result")

    } catch (e: Exception) {
        println("Error reading or processing input file: ${e.message}")
    }
}
