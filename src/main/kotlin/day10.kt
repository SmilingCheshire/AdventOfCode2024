import java.io.File
import java.util.*

fun main() {
    val inputFileName = "src/main/kotlin/inputd10"
    val map = File(inputFileName).readLines().map { line -> line.map { it - '0' } }
    val numRows = map.size
    val numCols = map[0].size

    fun isInBounds(row: Int, col: Int): Boolean = row in 0 until numRows && col in 0 until numCols

    fun bfsTrailheadScore(startRow: Int, startCol: Int): Int {
        val visited = Array(numRows) { BooleanArray(numCols) }
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(startRow to startCol)
        visited[startRow][startCol] = true

        val reachableNines = mutableSetOf<Pair<Int, Int>>()

        while (queue.isNotEmpty()) {
            val (row, col) = queue.poll()
            val currentHeight = map[row][col]

            if (currentHeight == 9) {
                reachableNines.add(row to col)
                continue
            }

            for ((dr, dc) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
                val newRow = row + dr
                val newCol = col + dc
                if (isInBounds(newRow, newCol) &&
                    !visited[newRow][newCol] &&
                    map[newRow][newCol] == currentHeight + 1
                ) {
                    visited[newRow][newCol] = true
                    queue.add(newRow to newCol)
                }
            }
        }
        return reachableNines.size
    }

    var totalScore = 0
    for (row in 0 until numRows) {
        for (col in 0 until numCols) {
            if (map[row][col] == 0) {
                totalScore += bfsTrailheadScore(row, col)
            }
        }
    }

    println("Total sum of trailhead scores: $totalScore")
}
