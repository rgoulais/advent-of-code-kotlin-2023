import kotlin.system.exitProcess

const val OUT = '.'
const val IN = 'X'
const val UNKNOWN = 'U'

class Solver10(inputStr: MutableList<String>) {

    val input = inputStr.map { it.toMutableList() }.toMutableList()

    private val maxCol = input[0].size
    private val maxRow = input.size
    var steps = 0
    private val pipes = mutableListOf<Pair<Int, Int>>()
    private val regexesIn = listOf(Regex("^F-*J"), Regex("^L-*7"))
    private val regexesOut = listOf(Regex("^F-*7"), Regex("^L-*J"))

    init {
        val (origin, premiersPossibles) = findS()
        pipes.add(origin)
        var newPossibles: List<Pair<Pair<Int, Int>, Pair<Int, Int>>> = premiersPossibles
            .map { origin to it }
            .mapNotNull {
                val result = findNext(it.first, it.second)
                if (result != null) it.second to result
                else null
            }
        pipes += newPossibles.map { it.first }
        steps += 1
        while (true) {
            val possibles = newPossibles
            newPossibles = mutableListOf()
            for (p in possibles) {
                val next = findNext(p.first, p.second)
                if (next != null)
                    newPossibles.add(p.second to next)
            }
            steps += 1
            if (newPossibles.first().first == newPossibles.last().first) {
                pipes += newPossibles.first().first
                break
            } else
                pipes += newPossibles.map { it.first }
        }

    }

    private fun possibilities(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        if (point.first < 0 || point.first >= maxRow || point.second < 0 || point.second >= maxCol)
            return listOf()
        return when (input[point.first][point.second]) {
            'S' -> listOf(
                point.first to point.second - 1,
                point.first to point.second + 1,
                point.first - 1 to point.second,
                point.first + 1 to point.second
            )

            'L' -> listOf(point.first - 1 to point.second, point.first to point.second + 1)
            'J' -> listOf(point.first - 1 to point.second, point.first to point.second - 1)
            '7' -> listOf(point.first + 1 to point.second, point.first to point.second - 1)
            'F' -> listOf(point.first + 1 to point.second, point.first to point.second + 1)
            '-' -> listOf(point.first to point.second - 1, point.first to point.second + 1)
            '|' -> listOf(point.first - 1 to point.second, point.first + 1 to point.second)
            else -> listOf()
        }
    }

    private fun findNext(previous: Pair<Int, Int>, current: Pair<Int, Int>): Pair<Int, Int>? {
        val poss = possibilities(current)
        if (previous in poss)
            return poss.first { it != previous }
        return null
    }

    private fun findS(): Pair<Pair<Int, Int>, List<Pair<Int, Int>>> {
        for (x in input.indices)
            for (y in input[x].indices)
                if (input[x][y] == 'S') {
                    val origin = x to y
                    return origin to possibilities(origin)
                }
        return (0 to 0) to listOf()
    }


    private fun checkSide(row: Int): MutableList<Char> {
        var inside = false
        var str = input[row].joinToString(separator = "")
        val ret = mutableListOf<Char>()
        boucle@ while (str.isNotEmpty()) {
            if (str.startsWith(UNKNOWN)) {
                ret += if (inside) IN else OUT
                str = str.substring(1)
                continue@boucle
            }
            for (r in regexesIn) {
                val match = r.find(str)?.value
                if (match != null) {
                    str = str.substring(match.length)
                    ret += match.toList()
                    inside = inside.not()
                    continue@boucle
                }
            }
            for (r in regexesOut) {
                val match = r.find(str)?.value
                if (match != null) {
                    str = str.substring(match.length)
                    ret += match.toList()
                    continue@boucle
                }
            }
            if (str.startsWith('|')) {
                str = str.substring(1)
                ret += '|'
                inside = inside.not()
                continue@boucle
            }
            for (c in listOf(IN, OUT))
                if (str.startsWith(c)) {
                    ret += c
                    str = str.substring(1)
                    continue@boucle
                }
        }
        return ret
    }

    fun replaceSByPipe() {
        val origin = pipes[0]
        val fmatch = pipes[1]
        val smatch = pipes[2]
        val rdiff = fmatch.first - smatch.first
        val cdiff = fmatch.second - smatch.second
        when (rdiff to cdiff) {
            -1 to 1 -> input[origin.first][origin.second] = 'F'
            -1 to -1 -> input[origin.first][origin.second] = '7'
            else -> {
                println(rdiff to cdiff)
                println("Cas non géré")
                exitProcess(0)
            }
        }
    }

    fun countInsidePoints(): Int {
        replaceSByPipe()
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (!pipes.contains(i to j))
                    input[i][j] = UNKNOWN
            }
        }
        for (i in input.indices) {
            input[i] = checkSide(i)
        }
        return input.sumOf { it.count { c -> c == IN } }
    }
}

fun main() {

    // '|' '-' 'L' 'J' '7' 'F'


    fun part1(input: List<String>): Int {
        val solver = Solver10(input.toMutableList())
        return solver.steps
    }

    fun part2(input: List<String>): Int {
        val solver = Solver10(input.toMutableList())
        return solver.countInsidePoints()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day10_test1")
    check(part1(testInput1) == 4)
    val testInput2 = readInput("Day10_test2")
    check(part1(testInput2) == 8)
    println("Tests 1 Ok")
    val input = readInput("Day10")
    result(System.currentTimeMillis(), part1(input), System.currentTimeMillis())
    check(part2(readInput("Day10_test3")) == 4)
    check(part2(readInput("Day10_test4")) == 4)
    check(part2(readInput("Day10_test5")) == 8)
    check(part2(readInput("Day10_test6")) == 10)
    println("Tests 2 Ok")
    result(System.currentTimeMillis(), part2(input), System.currentTimeMillis())
}
