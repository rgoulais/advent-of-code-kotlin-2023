import kotlin.math.max

fun main() {

    fun parseGame(game: String) = game.split(";").map { s ->
        s.split(",").map {
            val (_, amount, color) = it.split(" ")
            color to amount.toInt()
        }
    }

    fun getGameData(line: String): Pair<Int, List<List<Pair<String, Int>>>> {
        val (gameHeaders, gameBody) = line.split(":")
        val (_, gameId) = gameHeaders.split(" ")
        return gameId.toInt() to parseGame(gameBody)
    }

    fun part1(input: List<String>): Int {
        val limits = mapOf("red" to 12, "green" to 13, "blue" to 14)
        var total = 0
        for (line in input) {
            val (gameId, gameData) = getGameData(line)
            var good = true
            loop@ for (games in gameData) {
                for ((color, amount) in games) {
                    if (limits[color]!! < amount) {
                        good = false
                        break@loop
                    }
                }
            }
            if (good) {
                total += gameId
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val (_, gameData) = getGameData(line)
            val localdata = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            for (games in gameData) {
                for ((color, amount) in games) {
                    localdata[color] = localdata[color]?.let { max(it, amount) }!!
                }
            }
            total += localdata.values.reduce { acc, entry -> acc * entry }
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day02_test1")
    check(part1(testInput1) == 8)
    check(part2(testInput1) == 2286)

    val input = readInput("Day02")
    val debut = System.currentTimeMillis()
    part1(input).println()
    val part1 = System.currentTimeMillis()
    println("Temps d'exécution: ${part1 - debut} ms")
    part2(input).println()
    val part2 = System.currentTimeMillis()
    println("Temps d'exécution: ${part2 - part1} ms")
}
