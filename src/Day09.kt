fun main() {

    fun getFinalValue(liste: List<Int>): Int {
        return if (liste.all { it == 0 }) {
            0
        } else {
            liste.last() + getFinalValue(liste.zipWithNext().map { it.second - it.first })
        }
    }

    fun getFirstValue(liste: List<Int>): Int {
        return if (liste.all { it == 0 }) {
            0
        } else {
            liste.first() - getFirstValue(liste.zipWithNext().map { it.second - it.first })
        }
    }

    fun part1(input: List<String>): Int {
        var total = 0
        for (ligne in input) {
            val seq = ligne.split(" ").map { it.toInt() }
            total += getFinalValue(seq)
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (ligne in input) {
            val seq = ligne.split(" ").map { it.toInt() }
            total +=  getFirstValue(seq)
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)
    println("Tests Ok")
    val input = readInput("Day09")
    result(System.currentTimeMillis(), part1(input), System.currentTimeMillis())
    result(System.currentTimeMillis(), part2(input), System.currentTimeMillis())
}
