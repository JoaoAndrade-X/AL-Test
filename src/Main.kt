import java.util.Scanner

fun main() {
    val liveCells = readLifeInput()
    var currentGeneration = liveCells

    repeat(10) {
        currentGeneration = nextGeneration(currentGeneration)
    }

    println("#Life 1.06")
    currentGeneration.sortedBy { (x, y) -> x }.forEach { (x, y) ->
        println("$x $y")
    }
}

fun readLifeInput(): Set<Pair<Long, Long>> {
    val liveCells = mutableSetOf<Pair<Long, Long>>()
    val scanner = Scanner(System.`in`)

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine().trim()
        if (line.startsWith("#")) continue
        if (line.isNotEmpty()) {
            val parts = line.split(" ")
            val x = parts[0].toLong()
            val y = parts[1].toLong()
            liveCells.add(Pair(x, y))
        }
    }
    return liveCells
}

fun getNeighbors(cell: Pair<Long, Long>): List<Pair<Long, Long>> {
    val (x, y) = cell
    return listOf(
        Pair(x - 1, y - 1), Pair(x - 1, y), Pair(x - 1, y + 1),
        Pair(x, y - 1), Pair(x, y + 1),
        Pair(x + 1, y - 1), Pair(x + 1, y), Pair(x + 1, y + 1)
    )
}

fun nextGeneration(liveCells: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    val neighborCounts = mutableMapOf<Pair<Long, Long>, Int>()

    for (cell in liveCells) {
        for (neighbor in getNeighbors(cell)) {
            neighborCounts[neighbor] = neighborCounts.getOrDefault(neighbor, 0) + 1
        }
    }

    val newLiveCells = mutableSetOf<Pair<Long, Long>>()
    for ((cell, count) in neighborCounts) {
        if (count == 3 || (count == 2 && cell in liveCells)) {
            newLiveCells.add(cell)
        }
    }

    return newLiveCells
}