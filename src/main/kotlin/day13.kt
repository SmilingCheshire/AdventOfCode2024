import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd13"
    val machines = parseInput(inputFileName)

    val maxPresses = 100
    val results = machines.mapNotNull { machine ->
        findMinCost(machine, maxPresses)
    }

    val totalPrizes = results.size
    val totalTokens = results.sum()

    println("Prizes won: $totalPrizes")
    println("Minimum tokens spent: $totalTokens")
}

data class Machine(
    val ax: Int, val ay: Int, // Button A movements
    val bx: Int, val by: Int, // Button B movements
    val px: Int, val py: Int  // Prize location
)

fun parseInput(fileName: String): List<Machine> {
    val lines = File(fileName).readLines().filter { it.isNotBlank() }
    val machines = mutableListOf<Machine>()

    for (i in lines.indices step 3) {
        val buttonALine = lines[i].substringAfter("Button A: ").split(", ")
        val buttonBLine = lines[i + 1].substringAfter("Button B: ").split(", ")
        val prizeLine = lines[i + 2].substringAfter("Prize: ").split(", ")

        val ax = buttonALine[0].substringAfter("X+").toInt()
        val ay = buttonALine[1].substringAfter("Y+").toInt()
        val bx = buttonBLine[0].substringAfter("X+").toInt()
        val by = buttonBLine[1].substringAfter("Y+").toInt()
        val px = prizeLine[0].substringAfter("X=").toInt()
        val py = prizeLine[1].substringAfter("Y=").toInt()

        machines.add(Machine(ax, ay, bx, by, px, py))
    }

    return machines
}

fun findMinCost(machine: Machine, maxPresses: Int): Int? {
    var minCost: Int? = null

    for (x in 0..maxPresses) {
        for (y in 0..maxPresses) {
            if (x * machine.ax + y * machine.bx == machine.px &&
                x * machine.ay + y * machine.by == machine.py) {
                val cost = 3 * x + y
                minCost = if (minCost == null) cost else minOf(minCost, cost)
            }
        }
    }

    return minCost
}