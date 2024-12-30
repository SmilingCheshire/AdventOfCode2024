import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd6"

    try {
        val map = File(inputFileName).readLines().map { it.toCharArray() }

        // Find the starting position and direction
        var guardPosition = Pair(0, 0)
        var direction = 0 // 0 = Up, 1 = Right, 2 = Down, 3 = Left

        outer@ for (y in map.indices) {
            for (x in map[y].indices) {
                when (map[y][x]) {
                    '^' -> {
                        guardPosition = Pair(x, y)
                        direction = 0
                        break@outer
                    }
                    '>' -> {
                        guardPosition = Pair(x, y)
                        direction = 1
                        break@outer
                    }
                    'v' -> {
                        guardPosition = Pair(x, y)
                        direction = 2
                        break@outer
                    }
                    '<' -> {
                        guardPosition = Pair(x, y)
                        direction = 3
                        break@outer
                    }
                }
            }
        }

        // Movement directions (dx, dy): Up, Right, Down, Left
        val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))

        val visited = mutableSetOf(guardPosition)
        val height = map.size
        val width = map[0].size

        while (true) {
            val (x, y) = guardPosition
            val (dx, dy) = directions[direction]

            val nextPosition = Pair(x + dx, y + dy)

            if (nextPosition.first !in 0 until width || nextPosition.second !in 0 until height) {
                // Guard leaves the map
                break
            }

            if (map[nextPosition.second][nextPosition.first] == '#') {
                // Obstacle: turn right
                direction = (direction + 1) % 4
            } else {
                // Move forward
                guardPosition = nextPosition
                visited.add(guardPosition)
            }
        }

        println("Distinct positions visited: ${visited.size}")

    } catch (e: Exception) {
        println("Error reading or processing input file: ${e.message}")
    }
}
