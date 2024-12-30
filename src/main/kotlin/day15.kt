import java.io.File

fun main() {
    // Read input file
    val inputData = File("src/main/kotlin/inputd15").readText()

    // Parse the warehouse layout and robot's movement sequence
    val warehouseGrid = inputData.substringBefore("\n\n").lines().map { it.toCharArray() }.toMutableList()
    val robotMoves = inputData.substringAfter("\n\n").trim()

    warehouseGrid.forEach { println(it.concatToString()) }

    // Execute the moves
    val finalGrid = warehouseGrid.executeRobotMoves(robotMoves)

    // Calculate the sum of GPS coordinates of all boxes ('O')
    val totalGpsSum = finalGrid.findAllOccurrences('O').sumOf { it.calculateGps() }

    // Output the result
    println("Sum of GPS coordinates: $totalGpsSum")
}

private fun List<CharArray>.executeRobotMoves(robotMoves: String): List<CharArray> {
    val moveDirections = mapOf(
        '<' to Coordinate.LEFT,
        '>' to Coordinate.RIGHT,
        '^' to Coordinate.UP,
        'v' to Coordinate.DOWN
    )

    val robotStartPosition = findAllOccurrences('@').single()
    var currentRobotPosition = robotStartPosition

    robotMoves.forEach { move ->
        val direction = moveDirections[move] ?: return@forEach
        val nextPosition = currentRobotPosition + direction

        when (this[nextPosition]) {
            '[', 'O', ']' ->
                findMovablePoints(nextPosition, direction).also {
                    if (it.isNotEmpty()) currentRobotPosition = nextPosition
                }.forEach { point ->
                    this[point + direction] = this[point]
                    this[point] = '.'
                }
            !in "#" -> currentRobotPosition = nextPosition
        }
    }

    return this
}

private fun List<CharArray>.findMovablePoints(startPoint: Coordinate, direction: Coordinate): List<Coordinate> {
    val pointsQueue = ArrayDeque(listOf(startPoint))
    val visitedPoints = mutableSetOf<Coordinate>()
    val pointsToMove = mutableListOf<Coordinate>()

    while (pointsQueue.isNotEmpty()) {
        val currentPoint = pointsQueue.removeFirst()
        if (currentPoint in visitedPoints) continue
        visitedPoints += currentPoint
        pointsToMove += currentPoint

        if (direction == Coordinate.UP || direction == Coordinate.DOWN) {
            when (this[currentPoint]) {
                '[' -> pointsQueue.add(currentPoint + Coordinate.RIGHT)
                ']' -> pointsQueue.add(currentPoint + Coordinate.LEFT)
            }
        }

        val adjacentPoint = currentPoint + direction
        when (this[adjacentPoint]) {
            '[', 'O', ']' -> pointsQueue.add(adjacentPoint)
            '#' -> return emptyList()
        }
    }

    return pointsToMove.reversed()
}

private fun List<CharArray>.findAllOccurrences(char: Char) =
    flatMapIndexed { rowIndex, row ->
        row.mapIndexed { columnIndex, cell ->
            if (cell == char) Coordinate(columnIndex, rowIndex) else null
        }
    }.filterNotNull()

private operator fun List<CharArray>.get(point: Coordinate) =
    this[point.y][point.x]

private operator fun List<CharArray>.set(point: Coordinate, char: Char) {
    this[point.y][point.x] = char
}

private fun Coordinate.calculateGps() =
    100 * y + x

private data class Coordinate(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)

    companion object {
        val UP = Coordinate(0, -1)
        val DOWN = Coordinate(0, 1)
        val LEFT = Coordinate(-1, 0)
        val RIGHT = Coordinate(1, 0)
    }
}
