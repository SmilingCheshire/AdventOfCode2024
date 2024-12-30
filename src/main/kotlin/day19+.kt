import java.io.File

fun countWays(design: String, patterns: Set<String>, memo: MutableMap<String, Long>): Long {
    // Base case: an empty design has exactly one way to be constructed
    if (design.isEmpty()) return 1L

    // Check memoized results
    if (memo.containsKey(design)) return memo[design]!!

    // Initialize count
    var count = 0L

    // Try each pattern
    for (pattern in patterns) {
        if (design.startsWith(pattern)) {
            val remaining = design.substring(pattern.length)
            count += countWays(remaining, patterns, memo)
        }
    }

    // Store the result in memoization map
    memo[design] = count
    return count
}

fun main() {
    val input = File("src/main/kotlin/inputd19").readLines()

    // Parse the input
    val blankLineIndex = input.indexOf("")
    val patterns = input.subList(0, blankLineIndex).flatMap { it.split(", ") }.toSet()
    val designs = input.subList(blankLineIndex + 1, input.size)

    // Initialize memoization map
    val memo = mutableMapOf<String, Long>()

    // Sum up the number of ways for all designs
    val totalWays = designs.sumOf { design ->
        countWays(design, patterns, memo)
    }

    println("Total number of ways: $totalWays")
}
