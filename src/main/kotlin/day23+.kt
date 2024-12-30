import java.io.File
import kotlin.collections.*
import kotlin.math.*

fun main() {
    // Example input - replace with your actual input
    val input = File("src/main/kotlin/inputd23").readLines()

    // Constructing the graph
    val graph = buildMap<String, MutableList<String>> {
        input.forEach { line ->
            val (from, to) = line.split("-")
            getOrPut(from) { mutableListOf() } += to
            getOrPut(to) { mutableListOf() } += from
        }
    }

    // Helper to generate combinations of size k
    fun <T> List<T>.combinations(k: Int): Sequence<List<T>> {
        return sequence {
            val indices = IntArray(k) { it }
            while (true) {
                yield(indices.map { this@combinations[it] })
                var i = k - 1
                while (i >= 0 && indices[i] == this@combinations.size - k + i) i--
                if (i < 0) return@sequence
                indices[i]++
                for (j in i + 1 until k) indices[j] = indices[j - 1] + 1
            }
        }
    }

    fun List<String>.isClique(): Boolean =
        combinations(2).all { (i, j) -> i in graph.getValue(j) }

    fun solvePart2() =
        (14 downTo 4).firstNotNullOf { size ->
            graph.entries.firstNotNullOfOrNull { (v, n) ->
                (n + v).combinations(size)
                    .firstOrNull { it.isClique() }
                    ?.sorted()
                    ?.joinToString(",")
            }
        }



    // Solve the problem
    println(solvePart2())
}
