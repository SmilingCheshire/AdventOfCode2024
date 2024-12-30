import java.io.File
import java.util.*
import kotlin.collections.ArrayList

data class Point(val x: Int, val y: Int)

fun main() {
    val input = File("src/main/kotlin/inputd20").readLines()
    val rows = input.size
    val cols = input[0].length

    // Find start (S) and end (E) positions
    var start: Point? = null
    var end: Point? = null
    val grid = Array(rows) { CharArray(cols) }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            grid[i][j] = input[i][j]
            if (grid[i][j] == 'S') start = Point(i, j)
            if (grid[i][j] == 'E') end = Point(i, j)
        }
    }

    if (start == null || end == null) {
        println("Invalid map: Start or End not found")
        return
    }

    // Directions (Up, Down, Left, Right)
    val directions = arrayOf(
        Point(-1, 0),  // Up
        Point(1, 0),   // Down
        Point(0, -1),  // Left
        Point(0, 1)    // Right
    )

    // BFS to find shortest path without cheating
    fun bfs(start: Point, end: Point): Int {
        val queue: Queue<Point> = LinkedList()
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
                    queue.add(Point(newX, newY))
                }
            }
        }
        return dist[end.x][end.y]
    }

    // Perform BFS to get the initial shortest path distance without removing any walls
    val initialDistance = bfs(start!!, end!!)

    // Function to find all cheats and calculate time savings
    fun findCheats(): List<Int> {
        val timeSavings = mutableListOf<Int>()

        // Check all positions where we can apply the cheat (where walls exist)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (grid[i][j] == '#') {
                    // Try cheating by treating this wall as an open path (pass through)
                    val original = grid[i][j]
                    grid[i][j] = '.'  // Remove the wall temporarily

                    // Perform BFS with the cheat
                    val timeWithCheat = bfs(start!!, end!!)

                    // Restore the wall
                    grid[i][j] = original

                    // Calculate time saved, only if the path exists with cheat
                    if (timeWithCheat != Int.MAX_VALUE && timeWithCheat < initialDistance) {
                        val timeSaved = initialDistance - timeWithCheat
                        if (timeSaved >= 100) {
                            timeSavings.add(timeSaved)
                        }
                    }
                }
            }
        }

        return timeSavings
    }

    // Find cheats and count those that save at least 100 picoseconds
    val cheats = findCheats()
    val count = cheats.count()
    println("Number of cheats that save at least 100 picoseconds: $count")
}
