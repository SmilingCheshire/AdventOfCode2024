import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd6"

    try {
        val map = File(inputFileName).readLines().map { it.toCharArray() }

        // Find the starting position and direction
        var startPos = Pair(0, 0)
        var startDir = 0 // 0 = Up, 1 = Right, 2 = Down, 3 = Left

        outer@ for (y in map.indices) {
            for (x in map[y].indices) {
                when (map[y][x]) {
                    '^' -> {
                        startPos = Pair(x, y)
                        startDir = 0
                        break@outer
                    }
                    '>' -> {
                        startPos = Pair(x, y)
                        startDir = 1
                        break@outer
                    }
                    'v' -> {
                        startPos = Pair(x, y)
                        startDir = 2
                        break@outer
                    }
                    '<' -> {
                        startPos = Pair(x, y)
                        startDir = 3
                        break@outer
                    }
                }
            }
        }

        // Movement directions (dx, dy): Up, Right, Down, Left
        val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))

        fun simulate(map: List<CharArray>, startPos: Pair<Int, Int>, startDir: Int): Boolean {
            val visitedStates = mutableSetOf<Triple<Pair<Int, Int>, Int, Int>>() // (position, direction, step)
            var position = startPos
            var direction = startDir
            var step = 0

            while (true) {
                val (x, y) = position
                val (dx, dy) = directions[direction]

                // Detect loop: Same position and direction visited again
                val state = Triple(position, direction, step % directions.size)
                if (state in visitedStates) return true
                visitedStates.add(state)

                // Check next move
                val nextPosition = Pair(x + dx, y + dy)
                if (nextPosition.first !in map[0].indices || nextPosition.second !in map.indices) {
                    // Guard leaves the map
                    return false
                }

                if (map[nextPosition.second][nextPosition.first] == '#') {
                    // Obstacle: turn right
                    direction = (direction + 1) % 4
                } else {
                    // Move forward
                    position = nextPosition
                }

                step++
            }
        }

        // Count the number of positions where adding an obstruction causes a loop
        var loopCount = 0
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == '.') {
                    // Try adding an obstruction at this position
                    val modifiedMap = map.map { it.copyOf() }
                    modifiedMap[y][x] = '#'

                    if (simulate(modifiedMap, startPos, startDir)) {
                        loopCount++
                    }
                }
            }
        }

        println("Number of positions causing a loop: $loopCount")

    } catch (e: Exception) {
        println("Error reading or processing input file: ${e.message}")
    }
}
