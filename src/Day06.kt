fun main() {
    fun solve(input: String, size: Int) = input
        .windowed(size)
        .mapIndexed { index, sequence -> index to sequence }
        .first {
            it.second.toCharArray().toSet().size == size
        }
        .first + size

    fun part1(input: String): Int = solve(input, 4)

    fun part2(input: String): Int = solve(input, 14)

    val testInput = readInput("06.test")
    check(part1(testInput[0]) == 5)
    check(part1(testInput[1]) == 6)
    check(part1(testInput[2]) == 10)
    check(part1(testInput[3]) == 11)

    check(part2(testInput[0]) == 23)
    check(part2(testInput[1]) == 23)
    check(part2(testInput[2]) == 29)
    check(part2(testInput[3]) == 26)

    val input = readInputFull("06")
    println(part1(input))
    println(part2(input))
}
