const val BASE = 36
const val ARRIVEE = 46655


class Solver(val input: List<String>, private val zEnd: Boolean) {
    private val instructions = input[0]
    private val coordinates = getCoordinates()
    private val endsWithA = "a".toInt(BASE)
    private val endsWithZ = "z".toInt(BASE)

    private fun getCoordinates(): Map<Int, Pair<Int, Int>> {
        val data: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()
        for (i in 2..<input.size) {
            data[input[i].substring(0..2).toInt(BASE)] =
                input[i].substring(7..9).toInt(BASE) to input[i].substring(12..14).toInt(BASE)
        }
        return data
    }

    private fun getLocation(instructionIndex: Int, location: Int): Int {
        return if (instructions[instructionIndex] == 'L') coordinates[location]?.first!! else coordinates[location]?.second!!
    }

    fun getSteps(currentLocation: Int): Int {
        var i = 0
        var steps = 0
        val maxLeftRight = instructions.length
        var location = currentLocation
        while ( if (zEnd) (location % BASE != endsWithZ) else location != ARRIVEE) {
            location = getLocation(i,  location)
            steps += 1
            i = if (i == maxLeftRight - 1) 0 else i + 1
        }
        return steps
    }

    fun getEndsWithA(): MutableList<Int> {
        return coordinates.keys.filter { it % BASE == endsWithA }.toMutableList()
    }
}

fun main() {

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

    fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b


    fun part1(input: List<String>): Int {
        val solver = Solver(input, false)
        return solver.getSteps("AAA".toInt(BASE))
    }

    fun part2(input: List<String>): Long {
        val solver = Solver(input, true)
        val locations: MutableList<Int> = solver.getEndsWithA()
        val cycles = MutableList(locations.size) { 0L }
        for (j in locations.indices) {
            cycles[j] = solver.getSteps(locations[j]).toLong()
        }
        return cycles.reduce { a, b -> lcm(a, b) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput1) == 2)
    check(part1(testInput2) == 6)
    val testInput3 = readInput("Day08_test3")
    check(part2(testInput3) == 6L)
    println("Tests Ok")
    val input = readInput("Day08")
    result(System.currentTimeMillis(), part1(input), System.currentTimeMillis())
    result(System.currentTimeMillis(), part2(input), System.currentTimeMillis())
}
