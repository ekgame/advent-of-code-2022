fun main() {
    fun Pair<Int,Int>.fullyOverlaps(with: Pair<Int, Int>) = this.first >= with.first && this.second <= with.second
    fun Pair<Int,Int>.partiallyOverlaps(with: Pair<Int, Int>) = this.first <= with.second && with.first <= this.second

    fun part1(input: List<String>): Int = input
        .map { it.split(",").map { it.split("-").map { it.toInt() }.zipWithNext().first() } }
        .count { it[0].fullyOverlaps(it[1]) || it[1].fullyOverlaps(it[0]) }

    fun part2(input: List<String>): Int = input
        .map { it.split(",").map { it.split("-").map { it.toInt() }.zipWithNext().first() } }
        .count { it[0].partiallyOverlaps(it[1]) }

    val testInput = readInput("04.test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("04")
    println(part1(input))
    println(part2(input))
}
