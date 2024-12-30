import java.io.File

fun main() {
    val input = File("src/main/kotlin/inputd18").readLines()
    val bytes = input
        .map { Pt2D(it.substringBefore(",").toInt(), it.substringAfter(",").toInt()) }
        .withIndex()
        .associate { it.value to it.index }
        .withDefault { Int.MAX_VALUE }

    println("Part 1: " + solvePt1(bytes))
}

/** Solve Part 1: Find minimum steps for the first 1024 bytes */
fun solvePt1(bytes: Map<Pt2D, Int>): Int = minSteps(bytes, time = 1024)

/**
 * Calculates the minimum steps required to reach (70, 70) within the given time.
 * Returns -1 if no valid path exists.
 */
private fun minSteps(bytes: Map<Pt2D, Int>, time: Int): Int {
    val visited = mutableSetOf<Pt2D>()
    val toVisit = ArrayDeque<St>().apply { add(St(Pt2D(0, 0), 0)) }

    while (toVisit.isNotEmpty()) {
        val (current, score) = toVisit.removeFirst()

        // Check if we've reached the target
        if (current == Pt2D(70, 70)) return score

        // Explore neighbors
        for (neighbor in current.neighbours()) {
            if (neighbor.isInBounds() && neighbor !in visited && bytes.getValue(neighbor) > time) {
                visited += neighbor
                toVisit += St(neighbor, score + 1)
            }
        }
    }

    return -1 // No valid path found
}

/** Checks if a point is within the bounds of the grid */
private fun Pt2D.isInBounds(): Boolean = x in 0..70 && y in 0..70

/** Data class to represent a point in 2D space */
data class Pt2D(val x: Int, val y: Int) {
    /** Returns all possible neighboring points */
    fun neighbours(): List<Pt2D> = listOf(
        Pt2D(x + 1, y),
        Pt2D(x - 1, y),
        Pt2D(x, y + 1),
        Pt2D(x, y - 1)
    )
}

/** Data class to represent a state during BFS */
private data class St(val position: Pt2D, val score: Int)
