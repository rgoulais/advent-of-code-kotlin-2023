/**
 * The main method is the entry point of the program. It calculates the score for a given input of card hands.
 */
fun main() {
    val minStepHand = 10000000
    val base = 15
    fun getScoreHand(hand: String): Int {
        val handType = hand.groupingBy { it }.eachCount().values.sortedDescending()
        return when (handType[0]) {
            5 -> 60000000
            4 -> 50000000
            3 -> when (handType[1]) {
                2 -> 40000000
                else -> 30000000
            }

            2 -> when (handType[1]) {
                2 -> 20000000
                else -> minStepHand
            }
            else -> 0
        }
    }

    fun toHexa(hand: String, joker: Boolean): String {
        val jChar = if (joker) '0' else 'B'
        return hand.map { char ->
            when (char) {
                'A' -> 'E'
                'K' -> 'D'
                'Q' -> 'C'
                'J' -> jChar
                'T' -> 'A'
                else -> char
            }
        }.joinToString("")
    }

    check(toHexa("AAAAA", false).toInt(base) < minStepHand)

    fun combinations(chars: List<Char>, length: Int): List<String> {
        if (length == 0) {
            return listOf("")
        }
        val result = mutableListOf<String>()
        for (i in chars.indices) {
            val smallerCombinations = combinations(chars, length - 1)

            for (combination in smallerCombinations) {
                result.add(chars[i] + combination)
            }
        }
        return result
    }

    val possibles: MutableList<List<String>> = mutableListOf()
    val possible = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    for (i in 1..4) {
        possibles.add(combinations(possible, i))
    }

    fun calculateScore(hand: String, joker: Boolean): Int {
        var maxScore = getScoreHand(hand)
        if (joker) {
            val handWithoutJ = hand.replace("J", "")
            val numberOfJ = hand.length - handWithoutJ.length - 1
            if (numberOfJ in 0..3)
                for (c in possibles[numberOfJ]) {
                    maxScore = maxOf(maxScore, getScoreHand(c + handWithoutJ))
                    if (maxScore == 60000000)
                        break
                }
        }
        return toHexa(hand, joker).toInt(base) + maxScore
    }

    fun parseLines(input: List<String>, joker: Boolean): Int {
        var score = 0
        val sortedScores = input.map {
            val splt = it.split(" ")
            calculateScore(splt.first(), joker) to splt.last().toInt()
        }.sortedBy { it.first }
        for (i in sortedScores.indices) {
            score += (i + 1) * sortedScores[i].second
        }
        return score
    }

    fun part1(input: List<String>): Int {
        return parseLines(input, false)
    }

    fun part2(input: List<String>): Int {
        return parseLines(input, true)
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)
    println("Tests Ok")
    val input = readInput("Day07")
    result(System.currentTimeMillis(), part1(input), System.currentTimeMillis())
    result(System.currentTimeMillis(), part2(input), System.currentTimeMillis())
}
