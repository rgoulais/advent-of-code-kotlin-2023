import kotlin.math.pow

fun main() {

    fun parseGame(game: String) = game.split("|").map { s ->
        s.trim().replace("\\s+".toRegex(), " ").split(" ")
    }

    fun getGameData(line: String): Pair<Int, List<List<String>>> {
        val (gameHeaders, gameBody) = line.split(":")
        val gameData = gameHeaders.split(" ")
        return gameData.last().toInt() to parseGame(gameBody)
    }

    fun getNumMatch(wins: List<String>, game: List<String>) = wins.toSet().intersect(game.toSet()).size

    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val (_, gameData) = getGameData(line)
            total += 2.0.pow(getNumMatch(gameData[0], gameData[1]).toDouble() - 1).toInt()
        }
        return total
    }

    fun part2(input: List<String>): Int {
        val cards = List(input.size) { 1 }.toMutableList()
        for (i in input.indices) {
            val (_, gameData) = getGameData(input[i])
            for (j in 1..getNumMatch(gameData[0], gameData[1])) {
                cards[i + j] += cards[i]
            }
        }
        return cards.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    val debut = System.currentTimeMillis()
    part1(input).println()
    val part1 = System.currentTimeMillis()
    println("Temps d'exécution: ${part1 - debut} ms")
    part2(input).println()
    val part2 = System.currentTimeMillis()
    println("Temps d'exécution: ${part2 - part1} ms")
}
