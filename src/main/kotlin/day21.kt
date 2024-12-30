data class Position(val x: Int, val y: Int) {
    fun neighbours(): List<Position> = listOf(
        Position(x, y - 1), // UP
        Position(x + 1, y), // RIGHT
        Position(x, y + 1), // DOWN
        Position(x - 1, y)  // LEFT
    )

    operator fun minus(other: Position): Position = Position(x - other.x, y - other.y)

    companion object {
        val UP = Position(0, -1)
        val RIGHT = Position(1, 0)
        val DOWN = Position(0, 1)
        val LEFT = Position(-1, 0)
    }
}

private val numericKeypad = mapOf(
    Position(0, 0) to '7', Position(1, 0) to '8', Position(2, 0) to '9',
    Position(0, 1) to '4', Position(1, 1) to '5', Position(2, 1) to '6',
    Position(0, 2) to '1', Position(1, 2) to '2', Position(2, 2) to '3',
    Position(1, 3) to '0', Position(2, 3) to 'A'
)

private val directionalKeypad = mapOf(
    Position(1, 0) to '^', Position(2, 0) to 'A',
    Position(0, 1) to '<', Position(1, 1) to 'v', Position(2, 1) to '>'
)

private val codes = listOf("879A", "508A", "463A", "593A", "189A")

private val restrictedNumericPositions = setOf(Position(3, 0))
private val restrictedDirectionalPositions = setOf(Position(0, 0))

private val numericKeypadPaths = buildShortestPaths(keypad = numericKeypad, restricted = restrictedNumericPositions)

private val directionalKeypadPaths = buildShortestPaths(keypad = directionalKeypad, restricted = restrictedDirectionalPositions)

fun pt1() = solve(levels = 3)

fun pt2() = solve(levels = 26)

private fun solve(levels: Int) =
    codes.sumOf { code -> code.calculateCost(levels) * code.filter { it.isDigit() }.toInt() }

private fun String.calculateCost(
    levels: Int,
    keypadPaths: Map<Char, Map<Char, List<String>>> = numericKeypadPaths,
    cache: MutableMap<Pair<String, Int>, Long> = mutableMapOf()
): Long =
    cache.getOrPut(this to levels) {
        when (levels) {
            0 -> length.toLong()
            else -> {
                "A$this".zipWithNext().sumOf { (from, to) ->
                    keypadPaths.getValue(from).getValue(to).minOf { path ->
                        "${path}A".calculateCost(levels - 1, directionalKeypadPaths, cache)
                    }
                }
            }
        }
    }

private fun buildShortestPaths(
    keypad: Map<Position, Char>,
    restricted: Set<Position>
): Map<Char, Map<Char, List<String>>> =
    buildMap {
        for (start in keypad.keys) {
            if (start in restricted) continue // Skip restricted positions

            val paths = mutableMapOf<Char, MutableList<String>>()
            paths[keypad.getValue(start)] = mutableListOf("")

            val queue = mutableListOf(start to "")
            val visited = mutableSetOf<Position>()

            while (queue.isNotEmpty()) {
                val (current, path) = queue.removeFirst()
                visited += current

                current.neighbours().forEach {
                    if (it in keypad && it !in visited && it !in restricted) {
                        val newPath = path + (it - current).toChar()
                        queue += it to newPath
                        paths.getOrPut(keypad.getValue(it)) { mutableListOf() } += newPath
                    }
                }
            }

            set(keypad[start]!!, paths)
        }
    }

private fun Position.toChar() =
    when (this) {
        Position.UP -> "^"
        Position.RIGHT -> ">"
        Position.DOWN -> "v"
        Position.LEFT -> "<"
        else -> error("Invalid direction: $this")
    }

fun main() {
    println("Part 1: ${pt1()}")
    println("Part 2: ${pt2()}")
}
