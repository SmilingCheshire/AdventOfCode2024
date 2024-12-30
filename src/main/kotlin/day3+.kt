import java.io.File

fun main() {
    // Path to the input file (modify the path if needed)
    val inputFileName = "src/main/kotlin/inputd4"

    try {
        // Read input from the file
        val wordSearch = File(inputFileName).readLines()

        val target = "XMAS"
        val directions = listOf(
            Pair(0, 1),  // Horizontal (right)
            Pair(0, -1), // Horizontal (left)
            Pair(1, 0),  // Vertical (down)
            Pair(-1, 0), // Vertical (up)
            Pair(1, 1),  // Diagonal (down-right)
            Pair(-1, -1),// Diagonal (up-left)
            Pair(1, -1), // Diagonal (down-left)
            Pair(-1, 1)  // Diagonal (up-right)
        )

        var count = 0

        val rows = wordSearch.size
        val cols = wordSearch[0].length

        fun isValid(r: Int, c: Int): Boolean = r in 0 until rows && c in 0 until cols

        for (r in wordSearch.indices) {
            for (c in wordSearch[r].indices) {
                for ((dr, dc) in directions) {
                    var found = true
                    for (k in target.indices) {
                        val nr = r + dr * k
                        val nc = c + dc * k
                        if (!isValid(nr, nc) || wordSearch[nr][nc] != target[k]) {
                            found = false
                            break
                        }
                    }
                    if (found) count++
                }
            }
        }

        println("Total occurrences of XMAS: $count")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
