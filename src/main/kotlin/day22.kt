import java.io.File

fun evoSecret(secret: Long): Long {
    var result = secret
    // Multiply by 64, XOR, and prune
    result = (result xor (result * 64)) % 16777216
    // Divide by 32 (integer), XOR, and prune
    result = (result xor (result / 32)) % 16777216
    // Multiply by 2048, XOR, and prune
    result = (result xor (result * 2048)) % 16777216
    return result
}

fun findNthSecret(initial: Long, n: Int): Long {
    var current = initial
    repeat(n) {
        current = evoSecret(current)
    }
    return current
}

fun main() {
    // Read input file
    val input = File("src/main/kotlin/inputd22").readLines().map { it.toLong() }
    val nthSecretSum = input.sumOf { findNthSecret(it, 2000) }
    println("The sum of the 2000th secret numbers is: $nthSecretSum")
}
