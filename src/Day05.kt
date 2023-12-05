import kotlinx.coroutines.*

fun main() {
    fun parseLines(input: List<String>): Pair<List<Long>, List<List<List<Long>>>> {
        var seeds = listOf<Long>()
        val converters = mutableListOf<MutableList<List<Long>>>()
        for (line in input) {
            val trimmed = line.trim()
            if (trimmed.isEmpty())
                continue
            if (trimmed.startsWith("seeds:")) {
                seeds = trimmed.split(":").last().trim().split(" ").map { it.toLong() }
            } else if (trimmed.endsWith("map:")) {
                converters.add(mutableListOf<List<Long>>())
            } else {
                converters.last().add(trimmed.split(" ").map { it.toLong() })
            }
        }
        return seeds to converters
    }

    fun parseConverters(currentValue: Long, convertValues: List<List<Long>>): Long {
        for ((dest, src, inc) in convertValues)
            if ((currentValue >= src) && (currentValue < src + inc))
                return dest + currentValue - src
        return currentValue
    }


    fun part1(input: List<String>): Long {
        val (seeds, converters) = parseLines(input)
        var lowest = Long.MAX_VALUE
        for (seed in seeds) {
            var res = seed
            for (c in converters)
                res = parseConverters(res, c)
            if (res < lowest)
                lowest = res
        }
        return lowest
    }

    fun decouperEnPaires(liste: List<Long>): List<Pair<Long, Long>> {
        return liste.windowed(size = 2, step = 2, partialWindows = false) {
            it[0] to it[1]
        }
    }

    fun checkRange(firstSeed: Long, lenSeed: Long, converters: List<List<List<Long>>>): Long {
        var lowest = Long.MAX_VALUE
        for (seed in firstSeed..< (firstSeed + lenSeed)) {
            var res = seed
            for (c in converters)
                res = parseConverters(res, c)

            if (res < lowest)
                lowest = res
        }
        return lowest
    }

    fun part2(input: List<String>): Long {
        var lowest = Long.MAX_VALUE
        runBlocking {
            val (seeds, converters) = parseLines(input)
            val jobs = decouperEnPaires(seeds).map { (firstSeed, lenSeed) ->
                async(Dispatchers.Default) {
                    checkRange(firstSeed, lenSeed, converters)
                }
            }
            val resultats = jobs.map { it.await() }
            for (res in resultats) {
                if (res < lowest)
                    lowest = res
            }
        }
        return lowest
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput).toInt() == 35)
    check(part2(testInput).toInt() == 46)
    println("Tests Ok")
    val input = readInput("Day05")
    val debut = System.currentTimeMillis()
    part1(input).println()
    val part1 = System.currentTimeMillis()
    println("Temps d'exécution: ${part1 - debut} ms")
    part2(input).println()
    val part2 = System.currentTimeMillis()
    println("Temps d'exécution: ${part2 - part1} ms")
}
