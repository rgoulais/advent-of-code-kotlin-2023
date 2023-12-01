
val numbers = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun getMatch(indiceChaine: Int, chaine: String): String? {
    for (indiceNumber in numbers.indices) {
        if (chaine[indiceChaine].toString() == indiceNumber.toString()) {
            return indiceNumber.toString()
        }
        if (chaine.substring(indiceChaine).startsWith(numbers[indiceNumber])) {
            return indiceNumber.toString()
        }
    }
    return null
}

fun returnFirstOccurence(chaine: String): String {
    for (i in 0..chaine.length) {
        return getMatch(i, chaine) ?: continue
    }
    return ""
}

fun returnLastOccurence(chaine: String): String {
    for (i in chaine.length - 1 downTo 0) {
        return getMatch(i, chaine) ?: continue
    }
    return ""
}


fun cumul(chaine: String): String {
    return returnFirstOccurence(chaine) + returnLastOccurence(chaine)
}

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            val digits = line.replace("[^0-9]".toRegex(), "")
            total += (digits.first().toString() + digits.last().toString()).toInt()
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            total += cumul(line).toInt()
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
