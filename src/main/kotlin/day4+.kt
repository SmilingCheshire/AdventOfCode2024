import java.io.File

fun main() {
    // Path to the input file (modify as necessary)
    val inputFileName = "src/main/kotlin/inputd4"

    try {
        // Read the input file
        val wordSearch = File(inputFileName).readLines()

        val rows = wordSearch.size
        val cols = wordSearch[0].length
        val target1 = "MAS" // "MAS" forward
        val target2 = "SAM" // "MAS" backward
        var count = 0

        // Helper function to check if a diagonal forms the target pattern
        fun matchesDiagonal(r: Int, c: Int, dr: Int, dc: Int, target: String): Boolean {
            for (k in target.indices) {
                val nr = r + dr * k
                val nc = c + dc * k
                if (nr !in 0 until rows || nc !in 0 until cols || wordSearch[nr][nc] != target[k]) {
                    return false
                }
            }
            return true
        }

        // Scan every cell as a potential center of an X-MAS
        for (r in 1 until rows - 1) {
            for (c in 1 until cols - 1) {
                // Check if the diagonals form two MAS patterns
                val topLeftToBottomRightMatches =
                    matchesDiagonal(r - 1, c - 1, 1, 1, target1) || matchesDiagonal(r - 1, c - 1, 1, 1, target2)
                val bottomLeftToTopRightMatches =
                    matchesDiagonal(r + 1, c - 1, -1, 1, target1) || matchesDiagonal(r + 1, c - 1, -1, 1, target2)

                if (topLeftToBottomRightMatches && bottomLeftToTopRightMatches) {
                    count++
                }
            }
        }

        println("Total occurrences of X-MAS: $count")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
