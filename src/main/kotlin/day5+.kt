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

        // Function to order an update correctly based on rules
        fun correctOrder(update: List<Int>): List<Int> {
            val inUpdateSet = update.toSet()
            val filteredGraph = graph.filterKeys { it in inUpdateSet }
                .mapValues { (_, v) -> v.filter { it in inUpdateSet } }

            // Compute in-degrees for topological sorting
            val inDegree = mutableMapOf<Int, Int>().withDefault { 0 }
            filteredGraph.values.flatten().forEach { inDegree[it] = inDegree.getValue(it) + 1 }

            // Use a queue to perform topological sorting
            val queue = ArrayDeque<Int>().apply {
                update.forEach { if (inDegree.getValue(it) == 0) add(it) }
            }
            val sorted = mutableListOf<Int>()

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                sorted.add(current)
                for (neighbor in filteredGraph[current] ?: emptyList()) {
                    inDegree[neighbor] = inDegree.getValue(neighbor) - 1
                    if (inDegree.getValue(neighbor) == 0) {
                        queue.add(neighbor)
                    }
                }
            }

            return sorted
        }

        // Process updates and compute the result
        val incorrectlyOrdered = updates.filterNot { isValidOrder(it) }
        val correctedMiddlePages = incorrectlyOrdered.map { correctOrder(it) }
            .map { corrected -> corrected[corrected.size / 2] }

        val result = correctedMiddlePages.sum()
        println("The sum of middle pages from corrected updates is: $result")

    } catch (e: Exception) {
        println("Error reading or processing input file: ${e.message}")
    }
}
