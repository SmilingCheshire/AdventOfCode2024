import java.math.BigInteger

fun main() {
    // Input arrangement of stones
    val stones = mutableListOf(
        BigInteger("70949"),
        BigInteger("6183"),
        BigInteger("4"),
        BigInteger("3825336"),
        BigInteger("613971"),
        BigInteger("0"),
        BigInteger("15"),
        BigInteger("182")
    ) 

    val blinks = 25 // Number of blinks to simulate

    repeat(blinks) {
        // Create a new list for the updated stones
        val newStones = mutableListOf<BigInteger>()

        for (stone in stones) {
            when {
                // Rule 1: If the stone is engraved with 0, replace it with 1
                stone == BigInteger.ZERO -> newStones.add(BigInteger.ONE)

                // Rule 2: If the number has an even number of digits, split it
                stone.toString().length % 2 == 0 -> {
                    val digits = stone.toString()
                    val mid = digits.length / 2
                    val left = digits.substring(0, mid).toBigIntegerOrNull()
                    val right = digits.substring(mid).toBigIntegerOrNull()
                    if (left != null) newStones.add(left)
                    if (right != null) newStones.add(right)
                }

                // Rule 3: Multiply by 2024
                else -> newStones.add(stone * BigInteger("2024"))
            }
        }

        // Update the stones with the newly created arrangement
        stones.clear()
        stones.addAll(newStones)
    }

    // Output the total number of stones after 25 blinks
    println("Number of stones after $blinks blinks: ${stones.size}")
}
