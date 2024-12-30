import java.io.File

fun main() {
    val input = File("src/main/kotlin/inputd15").readLines()
    val originalMap = input.takeWhile { it.startsWith("#") }
    val moves = input.dropWhile { it.startsWith("#") }.joinToString("").trim()

    // Scale the map
    val scaledMap = originalMap.flatMap { line ->
        line.map { char ->
            when (char) {
                '#' -> "##"
                'O' -> "[]"
                '.' -> ".."
                '@' -> "@."
                else -> throw IllegalArgumentException("Invalid character in map: $char")
            }
        }.joinToString("").let { listOf(it, it) }
    }.map { it.toCharArray() }.toMutableList()

    // Find robot's initial position
    var robotRow = 0
    var robotCol = 0
    for (i in scaledMap.indices) {
        for (j in scaledMap[i].indices step 2) {
            if (scaledMap[i][j] == '@') {
                robotRow = i
                robotCol = j
                break
            }
        }
    }

    // Directions mapping
    val directions = mapOf(
        '<' to Pair(0, -2),
        '>' to Pair(0, 2),
        '^' to Pair(-1, 0),
        'v' to Pair(1, 0)
    )

    // Simulate the robot's movements
    for (move in moves) {
        val (dRow, dCol) = directions[move]!!
        val targetRow = robotRow + dRow
        val targetCol = robotCol + dCol

        if (scaledMap[targetRow][targetCol] == '.' && scaledMap[targetRow][targetCol + 1] == '.') {
            // Move the robot to the empty space
            scaledMap[robotRow][robotCol] = '.'
            scaledMap[robotRow][robotCol + 1] = '.'
            scaledMap[targetRow][targetCol] = '@'
            scaledMap[targetRow][targetCol + 1] = '.'
            robotRow = targetRow
            robotCol = targetCol
        } else if (scaledMap[targetRow][targetCol] == '[' && scaledMap[targetRow][targetCol + 1] == ']') {
            // Attempt to push the box
            val boxRow = targetRow + dRow
            val boxCol = targetCol + dCol

            if (scaledMap[boxRow][boxCol] == '.' && scaledMap[boxRow][boxCol + 1] == '.') {
                // Move the box and the robot
                scaledMap[robotRow][robotCol] = '.'
                scaledMap[robotRow][robotCol + 1] = '.'
                scaledMap[targetRow][targetCol] = '@'
                scaledMap[targetRow][targetCol + 1] = '.'
                scaledMap[boxRow][boxCol] = '['
                scaledMap[boxRow][boxCol + 1] = ']'
                robotRow = targetRow
                robotCol = targetCol
            }
        }
        // If movement is blocked, do nothing
    }

    // Calculate GPS coordinates
    var gpsSum = 0
    for (i in scaledMap.indices) {
        for (j in scaledMap[i].indices step 2) { // Skip to valid positions
            if (scaledMap[i][j] == '[' && scaledMap[i][j + 1] == ']') {
                val gpsCoordinate = 100 * i + (j / 2) // Convert scaled column
                gpsSum += gpsCoordinate
            }
        }
    }

    // Output the result
    println("Sum of GPS coordinates: $gpsSum")
}
