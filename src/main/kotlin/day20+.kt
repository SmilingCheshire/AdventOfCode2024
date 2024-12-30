import java.io.File
import java.util.*
import kotlin.collections.ArrayList

data class Pt(val x: Int, val y: Int)

fun main() {
    val input = File("src/main/kotlin/inputd20").readLines()
    val rows = input.size
    val cols = input[0].length

    // Find start (S) and end (E) positions
    var start: Pt? = null
    var end: Pt? = null
    val grid = Array(rows) { CharArray(cols) }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            grid[i][j] = input[i][j]
            if (grid[i][j] == 'S') start = Pt(i, j)
            if (grid[i][j] == 'E') end = Pt(i, j)
        }
    }

    if (start == null || end == null) {
        println("Invalid map: Start or End not found")
        return
    }

    // Directions (Up, Down, Left, Right)
    val directions = arrayOf(
        Pt(-1, 0),  // Up
        Pt(1, 0),   // Down
        Pt(0, -1),  // Left
        Pt(0, 1)    // Right
    )

    // BFS to find shortest path without cheating
    fun bfs(start: Pt, end: Pt): Int {
        val queue: Queue<Pt> = LinkedList()
        val visited = Array(rows) { BooleanArray(cols) }
        val dist = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }

        queue.add(start)
        visited[start.x][start.y] = true
        dist[start.x][start.y] = 0

        while (queue.isNotEmpty()) {
            val point = queue.poll()
            for (dir in directions) {
                val newX = point.x + dir.x
                val newY = point.y + dir.y
                if (newX in 0 until rows && newY in 0 until cols && !visited[newX][newY] && grid[newX][newY] != '#') {
                    visited[newX][newY] = true
                    dist[newX][newY] = dist[point.x][point.y] + 1
                    queue.add(Pt(newX, newY))
                }
            }
        }
        return dist[end.x][end.y]
    }

    val initialDistance = bfs(start!!, end!!)

    // Function to find all cheats and calculate time savings
    fun findCheats(): List<Int> {
        val timeSavings = mutableListOf<Int>()

        // Try cheating by passing through walls for up to 20 picoseconds
        for (cheatTime in 1..20) { // Cheat time can range from 1 to 20 picoseconds
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    if (grid[i][j] == '#') {
                        // Simulate the cheat by turning this wall into a track for a limited time
                        val original = grid[i][j]
                        grid[i][j] = '.' // Remove wall

                        // Perform BFS with the cheat time
                        val timeWithCheat = bfs(start!!, end!!)

                        // Restore the wall
                        grid[i][j] = original

                        // Calculate time saved by the cheat
                        if (timeWithCheat != Int.MAX_VALUE && timeWithCheat < initialDistance) {
                            val timeSaved = initialDistance - timeWithCheat
                            if (timeSaved >= 100) {
                                timeSavings.add(timeSaved)
                            }
                        }
                    }
                }
            }
        }

        return timeSavings
    }

    val cheats = findCheats()
    val count = cheats.count()
    println("Number of cheats that save at least 100 picoseconds: $count")
}
