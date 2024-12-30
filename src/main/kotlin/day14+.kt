import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd14"
    val input = File(inputFileName).readLines()

    val robots = input.map { Robot2.from(it) }
    var smallestBoundingBox = Long.MAX_VALUE
    var timeWithSmallestBox = 0

    // Choose to visualize at intervals of 10 seconds for example, or when the smallest bounding box occurs
    for (seconds in 7..10_000) {
        val positions = robots.map { it.positionAfter(seconds) }

        val minX = positions.minOf { it.x }
        val maxX = positions.maxOf { it.x }
        val minY = positions.minOf { it.y }
        val maxY = positions.maxOf { it.y }

        val boundingBoxArea = (maxX - minX).toLong() * (maxY - minY)

        if (boundingBoxArea < smallestBoundingBox) {
            smallestBoundingBox = boundingBoxArea
            timeWithSmallestBox = seconds

            // Optionally, visualize after the smallest bounding box occurs
            if (seconds == timeWithSmallestBox) {
                printMap(positions, seconds)
            }

        } else if (boundingBoxArea > smallestBoundingBox) {
            break // Bounding box started expanding; we found the best time
        }

        // Optionally visualize at regular intervals, for example every 10 seconds
        if (seconds % 10 == 0) {
            printMap(positions, seconds)
        }
    }

    println("Fewest seconds for Easter egg: $timeWithSmallestBox")
}

fun printMap(positions: List<Point2D>, seconds: Int) {
    val picture = Array(103) { Array(101) { '.' } }
    positions.forEach { picture[it.y][it.x] = 'X' }

    // Print the time and the map to the console
    println("num of sec = $seconds; map:")
    picture.forEach { println(it.joinToString("")) }
}

data class Point2D(val x: Int, val y: Int)

private data class Robot2(val px: Int, val py: Int, val vx: Int, val vy: Int) {
    fun positionAfter(seconds: Int) =
        Point2D(
            ((px + vx * seconds) % 101 + 101) % 101, // Ensure non-negative modular arithmetic
            ((py + vy * seconds) % 103 + 103) % 103
        )

    companion object {
        fun from(line: String) =
            Robot2(
                px = line.substringAfter("p=").substringBefore(",").toInt(),
                py = line.substringAfter(",", "").substringBefore(" ").toInt(),
                vx = line.substringAfter("v=").substringBefore(",").toInt(),
                vy = line.substringAfterLast(",").toInt(),
            )
    }
}
