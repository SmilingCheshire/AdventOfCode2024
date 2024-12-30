import java.io.File

fun evolveSecret(secret: Long): Long {
    var result = secret
    // Multiply by 64, XOR, and prune
    result = (result xor (result * 64)) % 16777216
    // Divide by 32 (integer), XOR, and prune
    result = (result xor (result / 32)) % 16777216
    // Multiply by 2048, XOR, and prune
    result = (result xor (result * 2048)) % 16777216
    return result
}

fun findPrices(initial: Long, n: Int): List<Int> {
    var current = initial
    val prices = mutableListOf<Int>()
    repeat(n) {
        current = evolveSecret(current)
        prices.add((current % 10).toInt()) // last digit is the price
    }
    return prices
}

fun findPriceChanges(prices: List<Int>): List<Int> {
    val changes = mutableListOf<Int>()
    for (i in 1 until prices.size) {
        changes.add(prices[i] - prices[i - 1]) // price change is the difference
    }
    return changes
}

fun getBananasForSequence(sequence: List<Int>, priceChanges: List<Int>, prices: List<Int>): Int {
    // Find the first occurrence of the sequence in price changes
    for (i in 0..(priceChanges.size - sequence.size)) {
        if (priceChanges.subList(i, i + sequence.size) == sequence) {
            return prices[i + sequence.size] // Return the price at the time of the match
        }
    }
    return 0 // No match found
}

fun main() {
    val input = File("src/main/kotlin/inputd22").readLines().map { it.toLong() }

    // We will track the total bananas we can get
    var totalBananas = 0

    // For each buyer (initial secret number)
    val allPriceChanges = mutableListOf<List<Int>>()
    val allPrices = mutableListOf<List<Int>>()

    // For each buyer, generate prices and price changes
    for (secret in input) {
        val prices = findPrices(secret, 2000)
        allPrices.add(prices)
        val priceChanges = findPriceChanges(prices)
        allPriceChanges.add(priceChanges)
    }

    // Now we need to find the best sequence of 4 price changes
    var bestSequence: List<Int>? = null
    var maxBananas = 0

    // Check all possible sequences of 4 price changes
    for (i in 0 until allPriceChanges[0].size - 4) {
        val sequence = allPriceChanges[0].subList(i, i + 4)
        var bananasForSequence = 0

        // Calculate bananas for this sequence for each buyer
        for (j in allPriceChanges.indices) {
            bananasForSequence += getBananasForSequence(sequence, allPriceChanges[j], allPrices[j])
        }

        // Update the best sequence if this gives more bananas
        if (bananasForSequence > maxBananas) {
            maxBananas = bananasForSequence
            bestSequence = sequence
        }
    }

    println("The most bananas you can get is: $maxBananas")
}
