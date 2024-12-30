import java.io.File

fun main() {
    // Read input from the file
    val input = File("src/main/kotlin/inputd24").readLines().joinToString("\n")

    // Parse the initial wire values
    val wireValues = parseInitialWireValues(input)

    // Parse the wire connections (gates and their operations)
    val wireConnections = parseWireConnections(input)

    // Calculate the expected binary sum by evaluating x and y wires
    val xValue = calculateBinaryValue("x", wireValues, wireConnections)
    val yValue = calculateBinaryValue("y", wireValues, wireConnections)
    val expectedSum = xValue + yValue

    // Identify the incorrect z wires
    val actualZValues = calculateBinaryValue("z", wireValues, wireConnections)
    val incorrectWires = findIncorrectWires(expectedSum, actualZValues, wireConnections)

    // Find the swapped gates
    val swapWires = findSwappedGates(incorrectWires, wireConnections)

    // Sort and print the swapped wires
    println(swapWires.sorted().joinToString(","))
}

data class Oper(val in1: String, val op: String, val in2: String)
// Parse the initial wire values from input
fun parseInitialWireValues(input: String): MutableMap<String, Boolean> {
    val wireValues = mutableMapOf<String, Boolean>()
    input.substringBefore("\n\n").lines().forEach { line ->
        val (wire, value) = line.split(": ")
        wireValues[wire] = value == "1"
    }
    return wireValues
}

// Parse the wire connections (gates and their operations)
fun parseWireConnections(input: String): Map<String, Oper> {
    val wireConnections = mutableMapOf<String, Oper>()
    input.substringAfter("\n\n").lines().forEach { line ->
        val (inputs, output) = line.split(" -> ")
        // Handle simple assignments (direct value to wire)
        if (inputs.contains(" ")) {
            val (in1, op, in2) = inputs.split(" ")
            wireConnections[output] = Oper(in1, op, in2)
        } else {
            wireConnections[output] = Oper(inputs, "", "")
        }
    }
    return wireConnections
}

// Calculate the binary value for wires starting with the given prefix
fun calculateBinaryValue(prefix: String, wireValues: MutableMap<String, Boolean>, wireConnections: Map<String, Oper>): Int {
    var binaryValue = 0
    var bitPosition = 0

    // Look for any wire with the given prefix and calculate its binary value
    while (wireValues.keys.any { it.startsWith(prefix) }) {
        val wireName = wireValues.keys.find { it.startsWith(prefix) } ?: break
        if (wireValues.containsKey(wireName)) {
            val bitValue = if (wireValues[wireName] == true) 1 else 0
            binaryValue = binaryValue or (bitValue shl bitPosition)
        }
        bitPosition++
    }

    return binaryValue
}

// Find incorrect z wires by comparing the expected sum to the actual sum
fun findIncorrectWires(expectedSum: Int, actualZValues: Int, wireConnections: Map<String, Oper>): List<String> {
    val incorrectWires = mutableListOf<String>()
    for (i in 0 until 5) {
        val expectedBit = (expectedSum shr i) and 1
        val actualBit = (actualZValues shr i) and 1
        if (expectedBit != actualBit) {
            incorrectWires.add("z$i")
        }
    }
    return incorrectWires
}

// Find the swapped gates by tracing back the incorrect wires
fun findSwappedGates(incorrectWires: List<String>, wireConnections: Map<String, Oper>): List<String> {
    val swappedWires = mutableListOf<String>()
    incorrectWires.forEach { wire ->
        val (in1, op, in2) = wireConnections[wire] ?: return@forEach

        // Collect wires involved in the swap
        swappedWires.add(in1)
        swappedWires.add(in2)
        swappedWires.add(wire)
    }

    return swappedWires
}

