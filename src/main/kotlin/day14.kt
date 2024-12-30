import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd14"
    val input = File(inputFileName).readLines()

    // Constants for the grid
    val width = 101
    val height = 103
    val time = 100

    // Parse input into robots' positions and velocities
    val robots = input.map { line ->
        val (pPart, vPart) = line.split(" ")
        val (px, py) = pPart.substringAfter("p=").split(",").map { it.toInt() }
        val (vx, vy) = vPart.substringAfter("v=").split(",").map { it.toInt() }
        Robot(px, py, vx, vy)
    }

    // Simulate robot positions after `time` seconds
    val finalPositions = robots.map { robot ->
        val finalX = (robot.px + robot.vx * time).mod(width).let { if (it < 0) it + width else it }
        val finalY = (robot.py + robot.vy * time).mod(height).let { if (it < 0) it + height else it }
        Pair(finalX, finalY)
    }

    // Count robots in each quadrant
    val middleX = width / 2
    val middleY = height / 2

    val quadrants = Array(4) { 0 } // [Q1, Q2, Q3, Q4]
    for ((x, y) in finalPositions) {
        when {
            x > middleX && y < middleY -> quadrants[0]++ // Q1
            x < middleX && y < middleY -> quadrants[1]++ // Q2
            x < middleX && y > middleY -> quadrants[2]++ // Q3
            x > middleX && y > middleY -> quadrants[3]++ // Q4
        }
    }

    // Calculate safety factor
    val safetyFactor = quadrants.reduce { acc, q -> acc * q }
    println("Safety factor after $time seconds: $safetyFactor")
}

data class Robot(val px: Int, val py: Int, val vx: Int, val vy: Int)
