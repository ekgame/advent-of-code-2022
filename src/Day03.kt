
fun main() {
    fun Char.priorityScore() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("Invalid for priority")
    }

    fun part1(input: List<String>): Int = input
        .map { it.chunked(it.length/2) }
        .sumOf {
            val set1 = it[0].toCharArray().toSet()
            val set2 = it[1].toCharArray().toSet()
            set1.intersect(set2).first().priorityScore()
        }

    fun part2(input: List<String>): Int = input
        .chunked(3)
        .sumOf {
            val set1 = it[0].toCharArray().toSet()
            val set2 = it[1].toCharArray().toSet()
            val set3 = it[2].toCharArray().toSet()
            set1.intersect(set2).intersect(set3).first().priorityScore()
        }

    val testInput = readInput("03.test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("03")
    println(part1(input))
    println(part2(input))
}
