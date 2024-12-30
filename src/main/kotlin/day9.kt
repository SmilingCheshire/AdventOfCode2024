//https://github.com/ClouddJR/advent-of-code-2024/blob/main/src/main/kotlin/com/clouddjr/advent2024/Day09.kt

fun solvePart1(input: String): Long {
    // Parse the disk map into spaces and files
    val (spaces, files) = parseDiskMap(input)

    // Flatten the blocks for spaces and files
    val spacesFlattened = spaces.flatMap { space ->
        (0 until space.length).map { offset -> Block(space.position + offset, 1, space.id) }
    }
    val filesFlattened = files.flatMap { file ->
        (0 until file.length).map { offset -> Block(file.position + offset, 1, file.id) }
    }

    // Calculate the result after compacting
    return calculateResult(spacesFlattened.toMutableList(), filesFlattened.toMutableList())
}

fun solvePart2(input: String): Long {
    // Parse the disk map into spaces and files
    val (spaces, files) = parseDiskMap(input)

    // Calculate the result directly without flattening
    return calculateResult(spaces, files)
}

private fun calculateResult(spaces: MutableList<Block>, files: MutableList<Block>): Long {
    // Iterate through files from the end to the start
    for (fileIndex in files.indices.reversed()) {
        val file = files[fileIndex]

        for (spaceIndex in spaces.indices) {
            val space = spaces[spaceIndex]

            // Stop if the space is beyond the file position
            if (space.position >= file.position) break

            // If the space can accommodate the file
            if (space.length >= file.length) {
                // Move the file to the space
                files[fileIndex] = Block(space.position, file.length, file.id)

                // Update the remaining space
                spaces[spaceIndex] = Block(space.position + file.length, space.length - file.length, space.id)
                break
            }
        }
    }

    // Calculate the checksum by summing position * id for each block
    return files.sumOf { file ->
        (0 until file.length).sumOf { offset -> file.id.toLong() * (file.position + offset) }
    }
}

private fun parseDiskMap(input: String): Pair<MutableList<Block>, MutableList<Block>> {
    val spaces = mutableListOf<Block>()
    val files = mutableListOf<Block>()

    var position = 0
    input.map { it.digitToInt() }.forEachIndexed { index, length ->
        if (index % 2 == 0) {
            // Add to files
            files.add(Block(position, length, index / 2))
        } else {
            // Add to spaces
            spaces.add(Block(position, length, -1))
        }
        position += length
    }

    return spaces to files
}

private data class Block(val position: Int, val length: Int, val id: Int)

fun main() {
    val inputFileName = "src/main/kotlin/inputd9"
    val input = java.io.File(inputFileName).readText().trim()

    println("Part 1: ${solvePart1(input)}")
    println("Part 2: ${solvePart2(input)}")
}
