import java.io.File
//https://github.com/ClouddJR/advent-of-code-2024/blob/main/src/main/kotlin/com/clouddjr/advent2024/Day13.kt
fun main() {
    val inputFileName = "src/main/kotlin/inputd13"
    val input = File(inputFileName).readLines()

    // Parse the input into claw machines
    val claws = input.chunked(4).map { lines ->
        val filteredLines = lines.filter { it.isNotBlank() }
        Claw(
            ax = filteredLines[0].substringAfter("X+").substringBefore(",").toInt(),
            ay = filteredLines[0].substringAfter("Y+").toInt(),
            bx = filteredLines[1].substringAfter("X+").substringBefore(",").toInt(),
            by = filteredLines[1].substringAfter("Y+").toInt(),
            x = filteredLines[2].substringAfter("X=").substringBefore(",").toLong(),
            y = filteredLines[2].substringAfter("Y=").toLong()
        )
    }

    // Solve part 1: Original prize positions
    val part1Tokens = claws.sumOf { it.tokens() }
    println("Part 1: Minimum tokens to win as many prizes as possible: $part1Tokens")

    // Solve part 2: Adjusted prize positions
    val part2Tokens = claws.map {
        it.copy(
            x = it.x + 10_000_000_000_000,
            y = it.y + 10_000_000_000_000
        )
    }.sumOf { it.tokens() }
    println("Part 2: Minimum tokens to win as many prizes as possible with adjusted positions: $part2Tokens")
}

data class Claw(
    val ax: Int, val ay: Int,
    val bx: Int, val by: Int,
    val x: Long, val y: Long
) {
    fun tokens(): Long {
        val denominator = (ax * by - ay * bx)
        if (denominator.toLong() == 0L) return 0 // Prevent division by zero

        // Calculate button presses
        val a = (x * by - y * bx) / denominator
        val b = (x - ax * a) / bx

        // Validate solution
        return if (a >= 0 && b >= 0 && a * ax + b * bx == x && a * ay + b * by == y) {
            3 * a + b
        } else {
            0
        }
    }
}
