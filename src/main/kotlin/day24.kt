import java.io.File

fun main() {
    val input = File("src/main/kotlin/inputd24").readLines().joinToString("\n")

    // Map to store the boolean values of each wire
    val wireValues = input.substringBefore("\n\n")
        .lines()
        .associate { it.substringBefore(":") to (it.substringAfter(": ") != "0") }
        .toMutableMap()

    // Map to store the operations for each wire (gates)
    val wireConnections = input.substringAfter("\n\n")
        .lines()
        .associate {
            val (in1, op, in2) = it.substringBefore(" ->").split(" ")
            it.substringAfter("-> ") to Operation(in1, op, in2)
        }

    // Solve part 1: Calculate the output value based on the z wires
    val result = wireConnections.keys
        .filter { it.startsWith("z") }
        .associateWith { it.evaluate(wireValues, wireConnections) }
        .entries.sortedByDescending { it.key }
        .map { if (it.value) 1 else 0 }
        .joinToString("")
        .toLong(2)

    println(result)
}

// Function to evaluate the wire's output based on the gate operations
private fun String.evaluate(wireValues: MutableMap<String, Boolean>, wireConnections: Map<String, Operation>): Boolean {
    return wireValues.getOrPut(this) {
        val (in1, op, in2) = wireConnections.getValue(this)

        // Evaluate the gate operation based on the inputs
        when (op) {
            "AND" -> in1.evaluate(wireValues, wireConnections) && in2.evaluate(wireValues, wireConnections)
            "XOR" -> in1.evaluate(wireValues, wireConnections) xor in2.evaluate(wireValues, wireConnections)
            "OR" -> in1.evaluate(wireValues, wireConnections) or in2.evaluate(wireValues, wireConnections)
            else -> error("Invalid operation: $op")
        }
    }
}

// Data class to hold the operation details for each wire
private data class Operation(val in1: String, val op: String, val in2: String)
