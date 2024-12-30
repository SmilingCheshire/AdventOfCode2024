import java.io.File

fun canFormDesign(design: String, patterns: Set<String>, memo: MutableMap<String, Boolean>): Boolean {
    // Base case: an empty design is always possible
    if (design.isEmpty()) return true

    // Check memoized results
    if (memo.containsKey(design)) return memo[design]!!

    // Try each pattern
    for (pattern in patterns) {
        if (design.startsWith(pattern)) {
            val remaining = design.substring(pattern.length)
            if (canFormDesign(remaining, patterns, memo)) {
                memo[design] = true
                return true
            }
        }
    }

    // If no pattern matches, mark as impossible
    memo[design] = false
    return false
}


fun main() {
    val input = File("src/main/kotlin/inputd19").readLines()

    // Parse the input
    val blankLineIndex = input.indexOf("")
    val patterns = input.subList(0, blankLineIndex).flatMap { it.split(", ") }.toSet()
    val designs = input.subList(blankLineIndex + 1, input.size)

    // Initialize memoization map
    val memo = mutableMapOf<String, Boolean>()

    // Count possible designs
    val possibleCount = designs.count { design ->
        canFormDesign(design, patterns, memo)
    }

    println("Number of possible designs: $possibleCount")
}
