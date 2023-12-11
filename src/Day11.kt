import kotlin.math.abs


fun main() {

    fun part1(input: List<String>, galaxyAge: Int): Long {
        var effectiveRow = 0
        var lastRow = 0
        val coordinatesToValue = mutableMapOf<Pair<Int, Int>, Int>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#') {
                    var ecartRow = (i - lastRow)
                    lastRow = i
                    if (ecartRow > 1)
                        ecartRow = 1 + (galaxyAge * (ecartRow - 1))
                    effectiveRow += ecartRow
                    coordinatesToValue[i to j] = effectiveRow
                }
            }
        }
        var effectiveCol = 0
        var lastCol = 0
        val tmpCoords = mutableListOf<Pair<Int, Int>>()
        for (j in input[0].indices) {
            for (i in input.indices) {
                if (input[i][j] == '#') {
                    var ecartCol = (j - lastCol)
                    lastCol = j
                    if (ecartCol > 1)
                        ecartCol = 1 + (galaxyAge * (ecartCol - 1))
                    effectiveCol += ecartCol
                    tmpCoords.add(((coordinatesToValue[i to j] to effectiveCol) as Pair<Int, Int>))
                }
            }
        }
        val coords = tmpCoords.sortedWith(compareBy({ it.first }, { it.second }))
        var total = 0L
        for (i in coords.indices)
            for (j in (i + 1)..<coords.size)
                total += abs(coords[j].first - coords[i].first) + abs(coords[j].second - coords[i].second)
        return total
    }

    check(part1(readInput("Day11_test1"), 2) == 374L)
    check(part1(readInput("Day11_test1"), 10) == 1030L)
    check(part1(readInput("Day11_test1"), 100) == 8410L)
    println("Tests Ok")
    val input = readInput("Day11")
    result(System.currentTimeMillis(), part1(input, 2), System.currentTimeMillis())
    result(System.currentTimeMillis(), part1(input, 1000000), System.currentTimeMillis())
}
