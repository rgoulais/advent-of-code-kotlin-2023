fun main() {
    fun parseInput(input: List<String>): Pair<List<Long>, List<Long>> {
        val times = input[0].replace("[^0-9]+".toRegex(), " ").trim().split(" ").map { it.toLong() }
        val distances = input[1].replace("[^0-9]+".toRegex(), " ").trim().split(" ").map { it.toLong() }
        return times to distances
    }

    fun parseInputNoSpace(input: List<String>): Pair<Long, Long> {
        val times = input[0].replace("[^0-9]+".toRegex(), "").toLong()
        val distances = input[1].replace("[^0-9]+".toRegex(), "").toLong()
        return times to distances
    }

    fun getWins(temps: Long, distance: Long): Int {
        for (start in 0..temps)
            if (start * (temps - start) > distance)
                for (end in temps.downTo(0))
                    if (end * (temps - end) > distance)
                        return (end - start + 1).toInt()
        return 0
    }

    fun part1(input: List<String>): Int {
        val (times, distances) = parseInput(input)
        var mulwin = 1
        for (i in times.indices) {
            mulwin *= getWins(times[i], distances[i])
        }
        return mulwin
    }

    fun part2(input: List<String>): Int {
        val (temps, distance) = parseInputNoSpace(input)
        return getWins(temps, distance)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)
    println("Tests Ok")
    val input = readInput("Day06")
    result(System.currentTimeMillis(), part1(input), System.currentTimeMillis())
    result(System.currentTimeMillis(), part2(input), System.currentTimeMillis())
}
