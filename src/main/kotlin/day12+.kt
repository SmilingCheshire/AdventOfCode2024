import java.io.File

fun main() {
    val inputFileName = "src/main/kotlin/inputd12"
    val garden = File(inputFileName).readLines()

    val day12 = Day12(garden)
    val totalCost = day12.solvePart2()
    println("Total price of fencing (bulk discount): $totalCost")
}

class Day12(private val garden: List<String>) {
    fun solvePart2() = findRegions().sumOf { it.area * it.sides }

    private fun findRegions(): List<Region> {
        val visited = mutableSetOf<Point2>()

        return garden.indices.flatMap { y ->
            garden[y].indices.mapNotNull { x ->
                val point = Point2(x, y)
                if (point !in visited) findRegion(point, visited) else null
            }
        }
    }

    private fun findRegion(current: Point2, visited: MutableSet<Point2>): Region {
        if (current in visited) return Region(0, 0)
        visited.add(current)

        val (inside, outside) = current.neighbours().partition {
            garden[current] == garden[it]
        }

        return inside.fold(
            Region(
                area = 1,
                sides = current.countCorners()
            )
        ) { region, neighbour ->
            region + findRegion(neighbour, visited)
        }
    }

    private operator fun List<String>.get(p: Point2): Char? =
        if (p.y in indices && p.x in this[p.y].indices) this[p.y][p.x] else null

    private fun Point2.countCorners(): Int {
        return listOf(Point2.UP, Point2.RIGHT, Point2.DOWN, Point2.LEFT, Point2.UP)
            .zipWithNext()
            .map { (d1, d2) ->
                listOf(
                    garden[this],
                    garden[this + d1],
                    garden[this + d2],
                    garden[this + d1 + d2]
                )
            }
            .count { (target, s1, s2, corner) ->
                (target != s1 && target != s2) || (target == s1 && target == s2 && target != corner)
            }
    }

    private data class Region(val area: Int, val sides: Int) {
        operator fun plus(other: Region) =
            Region(
                area + other.area,
                sides + other.sides
            )
    }
}

data class Point2(val x: Int, val y: Int) {
    fun neighbours() = listOf(
        this + UP,
        this + DOWN,
        this + LEFT,
        this + RIGHT
    )

    operator fun plus(other: Point2) = Point2(x + other.x, y + other.y)

    companion object {
        val UP = Point2(0, -1)
        val DOWN = Point2(0, 1)
        val LEFT = Point2(-1, 0)
        val RIGHT = Point2(1, 0)
    }
}
