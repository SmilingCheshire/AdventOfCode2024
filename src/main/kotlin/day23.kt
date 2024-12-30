import java.io.File

fun main() {
    // Read input and parse connections
    val connections = File("src/main/kotlin/inputd23").readLines()
    val graph = mutableMapOf<String, MutableSet<String>>()

    // Build the graph
    for (connection in connections) {
        val (a, b) = connection.split("-")
        graph.computeIfAbsent(a) { mutableSetOf() }.add(b)
        graph.computeIfAbsent(b) { mutableSetOf() }.add(a)
    }

    // Find all triangles
    val triangles = mutableSetOf<Set<String>>()
    for ((node, neighbors) in graph) {
        for (neighbor1 in neighbors) {
            for (neighbor2 in neighbors) {
                if (neighbor1 < neighbor2 && graph[neighbor1]?.contains(neighbor2) == true) {
                    triangles.add(setOf(node, neighbor1, neighbor2))
                }
            }
        }
    }

    // Filter triangles containing a node starting with 't'
    val filteredTriangles = triangles.filter { triangle -> triangle.any { it.startsWith("t") } }

    // Output the count
    println("Number of triangles containing at least one computer starting with 't': ${filteredTriangles.size}")
}
