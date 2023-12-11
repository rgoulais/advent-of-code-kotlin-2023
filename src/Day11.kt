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
        val coords = mutableListOf<Pair<Int, Int>>()
        var total = 0L
        for ( (coord, row) in coordinatesToValue.toList().sortedBy { (key, _) -> key.second }.toMap()) {
            var ecartCol = (coord.second - lastCol)
            lastCol = coord.second
            if (ecartCol > 1)
                ecartCol = 1 + (galaxyAge * (ecartCol - 1))
            effectiveCol += ecartCol
            val newCoord = row to effectiveCol
            for (i in coords.indices)
                total += abs(newCoord.first - coords[i].first) + abs(newCoord.second - coords[i].second)
            coords.add(newCoord)
        }
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
