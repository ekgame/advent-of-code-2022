fun main() {
    fun part1(input: List<String>): Int {
        return input
            .fold(mutableListOf<MutableList<Int>>(mutableListOf())) { acc, s ->
                if (s.isBlank()) {
                    acc.add(mutableListOf())
                } else {
                    acc.last().add(s.toInt())
                }
                acc
            }
            .maxOfOrNull { it.sum() } ?: 0
    }

    fun part2(input: List<String>): Int {
        return input
            .fold(mutableListOf<MutableList<Int>>(mutableListOf())) { acc, s ->
                if (s.isBlank()) {
                    acc.add(mutableListOf())
                } else {
                    acc.last().add(s.toInt())
                }
                acc
            }
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("01.test")
    check(part1(testInput) == 24000)

    val input = readInput("01")
    println(part1(input))
    println(part2(input))
}
