import java.io.File
import java.util.PriorityQueue

fun main() {
    val input = File("src/main/kotlin/inputd16").readLines()
    val maze = input.map { it.toCharArray() }
    val start = findPoint(maze, 'S')
    val end = findPoint(maze, 'E')

    val directions = listOf(
        Pair(1, 0),  // East
        Pair(0, 1),  // South
        Pair(-1, 0), // West
        Pair(0, -1)  // North
    )

    val (lowestScore, pathTiles) = findBestPaths(maze, start, end, directions)
    println("The lowest score is: $lowestScore")
    println("The number of tiles part of at least one best path is: ${pathTiles.size}")

    // Mark tiles on the best paths
    val updatedMaze = maze.mapIndexed { y, row ->
        row.mapIndexed { x, char ->
            if (Pair(x, y) in pathTiles && char == '.') 'O' else char
        }.toCharArray()
    }
    println("Maze with best paths:")
    updatedMaze.forEach { println(it.joinToString("")) }
}

fun findPoint(maze: List<CharArray>, char: Char): Pair<Int, Int> {
    maze.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (c == char) return Pair(x, y)
        }
    }
    error("Character $char not found in the maze.")
}

fun findBestPaths(
    maze: List<CharArray>,
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    directions: List<Pair<Int, Int>>
): Pair<Int, Set<Pair<Int, Int>>> {
    val rows = maze.size
    val cols = maze[0].size
    val visited = Array(rows) { Array(cols) { IntArray(4) { Int.MAX_VALUE } } }
    val bestScorePaths = mutableListOf<List<Pair<Int, Int>>>()

    // Priority queue for the state (x, y, direction, score, path)
    val priorityQueue = PriorityQueue(compareBy<StateWithPath> { it.score })

    // Initial state: Start at 'S' facing East (direction 0)
    priorityQueue.add(StateWithPath(start.first, start.second, 0, 0, listOf(start)))

    var lowestScore = Int.MAX_VALUE

    while (priorityQueue.isNotEmpty()) {
        val current = priorityQueue.poll()

        // Skip if this state is worse than the lowest score found so far
        if (current.score > lowestScore) continue

        // Skip if we already found a better path to this state
        if (current.score >= visited[current.y][current.x][current.direction]) continue
        visited[current.y][current.x][current.direction] = current.score

        // Check if we reached the end
        if (current.x == end.first && current.y == end.second) {
            if (current.score < lowestScore) {
                lowestScore = current.score
                bestScorePaths.clear()
            }
            if (current.score == lowestScore) {
                bestScorePaths.add(current.path)
            }
            continue
        }

        // Move forward
        val forward = Pair(
            current.x + directions[current.direction].first,
            current.y + directions[current.direction].second
        )
        if (isValidMove(maze, forward)) {
            priorityQueue.add(
                StateWithPath(
                    forward.first, forward.second, current.direction,
                    current.score + 1, current.path + forward
                )
            )
        }

        // Rotate clockwise
        val clockwiseDirection = (current.direction + 1) % 4
        priorityQueue.add(
            StateWithPath(
                current.x, current.y, clockwiseDirection,
                current.score + 1000, current.path
            )
        )

        // Rotate counterclockwise
        val counterClockwiseDirection = (current.direction + 3) % 4
        priorityQueue.add(
            StateWithPath(
                current.x, current.y, counterClockwiseDirection,
                current.score + 1000, current.path
            )
        )
    }

    // Find the intersection of tiles from all best paths
    val commonPathTiles = bestScorePaths
        .map { it.toSet() } // Convert each path to a set
        .reduce { acc, path -> acc.intersect(path) } // Find the intersection of all paths

    return Pair(lowestScore, commonPathTiles)
}

fun isValidMove(maze: List<CharArray>, point: Pair<Int, Int>): Boolean {
    return point.second in maze.indices && point.first in maze[0].indices && maze[point.second][point.first] != '#'
}

// Data class for state representation with path tracking
data class StateWithPath(
    val x: Int,
    val y: Int,
    val direction: Int,
    val score: Int,
    val path: List<Pair<Int, Int>>
)