fun main() {
    fun parseLines(input: List<String>): Pair<List<Long>, MutableMap<String, MutableList<List<Long>>>> {
        var current = "seeds"
        var seeds = listOf<Long>()
        val converters = mutableMapOf<String, MutableList<List<Long>>>()
        for (line in input) {
            val trimmed = line.trim()
            if (trimmed.isEmpty())
                continue
            if (current == "seeds" && !trimmed.endsWith("map:")) {
                seeds = trimmed.split(":").last().trim().split(" ").map { it.toLong() }
            } else if (trimmed.endsWith("map:")) {
                current = trimmed.split(" ").first().split("-").last()
            } else {
                converters.getOrPut(current) { mutableListOf() }.add(trimmed.split(" ").map { it.toLong() })
            }
        }
        return seeds to converters
    }

    fun parseConverters(currentValue: Long, convertValues: List<List<Long>>): Long {
        for ((dest, src, inc) in convertValues) {
            if ((currentValue >= src) && (currentValue < src + inc))
                return dest + currentValue - src
        }
        return currentValue
    }


    fun part1(input: List<String>): Long {
        val (seeds, converters) = parseLines(input)
        var lowest = Long.MAX_VALUE
        for (seed in seeds) {
            var res = seed
            for (k in listOf("soil", "fertilizer", "water", "light", "temperature", "humidity", "location")) {
                res = converters[k]?.let { parseConverters(res, it) }!!
            }
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

    fun part2(input: List<String>): Long {
        val (seeds, converters) = parseLines(input)
        var lowest = Long.MAX_VALUE
        for ((firstSeed, lenSeed) in decouperEnPaires(seeds)) {
            for (seed in firstSeed..<firstSeed + lenSeed) {
                var res = seed
                for (k in listOf("soil", "fertilizer", "water", "light", "temperature", "humidity", "location")) {
                    res = converters[k]?.let { parseConverters(res, it) }!!
                }
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
