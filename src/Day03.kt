fun main() {
    fun isInBound(input: List<String>, x: Int, y: Int) = x >= 0 && y >= 0 && x < input.size && y < input[x].length

    fun isNumber(input: List<String>, x: Int, y: Int) = isInBound(input, x, y) && input[x][y].isDigit()

    fun isSymbol(input: List<String>, x: Int, y: Int) =
        isInBound(input, x, y) && input[x][y].toString().matches("[^0-9.]".toRegex())

    fun findNumberAtPos(input: List<String>, x: Int, y: Int): String {
        var s = ""
        if (isNumber(input, x, y)) {
            s = input[x][y].toString()
            var myY = y - 1
            while (isNumber(input, x, myY)) {
                s = input[x][myY].toString() + s
                myY -= 1
            }
            myY = y + 1
            while (isNumber(input, x, myY)) {
                s += input[x][myY].toString()
                myY += 1
            }
        }
        return s
    }

    fun hasSymbolsAround(input: List<String>, x: Int, y: Int, s: String): Boolean {
        for (i in x - 1..x + 1)
            for (j in y - 1..y + s.length)
                if (isSymbol(input, i, j))
                    return true
        return false
    }

    fun part1(input: List<String>): Int {
        var total = 0
        for (i in input.indices) {
            var j = 0
            while (j < input[i].length) {
                val s = findNumberAtPos(input, i, j)
                if (s.isNotEmpty() && hasSymbolsAround(input, i, j, s)) {
                    total += s.toInt()
                }
                j += s.length + 1
            }
        }
        return total
    }

    fun findNumbersAround(input: List<String>, x: Int, y: Int): MutableList<String> {
        val result: MutableList<String> = mutableListOf()
        if (isInBound(input, x, y) && input[x][y] == '*') {
            for (myX in listOf(x - 1, x + 1)) {
                val s = findNumberAtPos(input, myX, y)
                if (s.isNotEmpty()) {
                    result.add(s)
                } else for (myY in listOf(y - 1, y + 1)) {
                    val s2 = findNumberAtPos(input, myX, myY)
                    if (s2.isNotEmpty())
                        result.add(s2)
                }
            }
            for (myY in listOf(y - 1, y + 1)) {
                val s = findNumberAtPos(input, x, myY)
                if (s.isNotEmpty())
                    result.add(s)
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                val lst = findNumbersAround(input, i, j)
                if (lst.size == 2) {
                    total += lst[0].toInt() * lst[1].toInt()
                }
            }
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
