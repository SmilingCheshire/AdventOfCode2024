import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd10"
    val map = File(inputFileName).readLines().map { line -> line.map { it - '0' } }
    val numRows = map.size
    val numCols = map[0].size

    fun isInBounds(row: Int, col: Int): Boolean = row in 0 until numRows && col in 0 until numCols

    fun dfsCountPaths(row: Int, col: Int, currentHeight: Int): Int {
        if (!isInBounds(row, col) || map[row][col] != currentHeight) return 0
        if (currentHeight == 9) return 1

        var count = 0
        for ((dr, dc) in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)) {
            count += dfsCountPaths(row + dr, col + dc, currentHeight + 1)
        }
        return count
    }

    var totalRating = 0
    for (row in 0 until numRows) {
        for (col in 0 until numCols) {
            if (map[row][col] == 0) {
                totalRating += dfsCountPaths(row, col, 0)
            }
        }
    }

    println("Total sum of trailhead ratings: $totalRating")
}
