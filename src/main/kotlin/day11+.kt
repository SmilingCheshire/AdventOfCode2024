//https://github.com/ClouddJR/advent-of-code-2024/blob/main/src/main/kotlin/com/clouddjr/advent2024/Day11.kt

private val stones = "70949 6183 4 3825336 613971 0 15 182"
    .split(" ")
    .map { it.toLong() }
    .groupingBy { it }
    .eachCount()
    .mapValues { it.value.toLong() }

fun solvePart1() = countStones(blinks = 25)

fun solvePart2() = countStones(blinks = 75)

private fun countStones(blinks: Int): Long {
    return (0 until blinks).fold(stones) { current, _ ->
        buildMap {
            current.entries.forEach { (key, count) ->
                when {
                    key == 0L -> {
                        merge(1, count, Long::plus)
                    }
                    key.toString().length % 2 == 0 -> {
                        val str = key.toString()
                        val mid = str.length / 2
                        merge(str.take(mid).toLong(), count, Long::plus)
                        merge(str.takeLast(mid).toLong(), count, Long::plus)
                    }
                    else -> {
                        merge(key * 2024, count, Long::plus)
                    }
                }
            }
        }
    }.values.sum()
}
fun main() {
    println(solvePart2())
}