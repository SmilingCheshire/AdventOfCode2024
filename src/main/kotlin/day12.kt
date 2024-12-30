import java.io.File

fun main() {
    // Read the input map from the file
    val inputFileName = "src/main/kotlin/inputd12"
    val map = File(inputFileName).readLines()

    // Calculate and print the total cost of fencing
    val totalCost = calculateTotalFenceCost(map)
    println("Total price of fencing: $totalCost")
}

fun calculateTotalFenceCost(map: List<String>): Int {
    val rows = map.size
    val cols = map[0].length
    val visited = Array(rows) { BooleanArray(cols) }

    var totalCost = 0

    val directions = listOf(
        Pair(-1, 0), // up
        Pair(1, 0),  // down
        Pair(0, -1), // left
        Pair(0, 1)   // right
    )

    fun isInBounds(x: Int, y: Int) = x in 0 until rows && y in 0 until cols

    // Flood-fill to calculate area and perimeter for a region
    fun floodFill(x: Int, y: Int, plantType: Char): Pair<Int, Int> {
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(Pair(x, y))
        visited[x][y] = true

        var area = 0
        var perimeter = 0

        while (queue.isNotEmpty()) {
            val (cx, cy) = queue.removeFirst()
            area++

            for ((dx, dy) in directions) {
                val nx = cx + dx
                val ny = cy + dy

                if (!isInBounds(nx, ny) || map[nx][ny] != plantType) {
                    perimeter++
                } else if (!visited[nx][ny]) {
                    visited[nx][ny] = true
                    queue.add(Pair(nx, ny))
                }
            }
        }

        return Pair(area, perimeter)
    }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (!visited[i][j]) {
                val plantType = map[i][j]
                val (area, perimeter) = floodFill(i, j, plantType)
                totalCost += area * perimeter
            }
        }
    }

    return totalCost
}
