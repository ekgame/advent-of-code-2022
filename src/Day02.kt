
fun main() {
    class GestureDefinition(
        val winAgainst: Char,
        val loseAgainst: Char,
        val drawAgainst: Char,
        val useScore: Int,
    ) {
        fun isWin(against: Char) = winAgainst == against
        fun isLoss(against: Char) = loseAgainst == against
        fun isDraw(against: Char) = drawAgainst == against
    }
    
    val ROCK = GestureDefinition('C', 'B', 'A', 1)
    val PAPER = GestureDefinition('A', 'C', 'B', 2)
    val SCISSORS = GestureDefinition('B', 'A', 'C', 3)
    val ALL = listOf(ROCK, PAPER, SCISSORS)

    fun getWinning(against: Char) = ALL.first { it.isWin(against) }
    fun getLosing(against: Char) = ALL.first { it.isLoss(against) }
    fun getDrawing(against: Char) = ALL.first { it.isDraw(against) }

    fun Pair<Char, GestureDefinition>.getScore(): Int {
        var result = this.second.useScore
        if (this.second.isWin(this.first)) result += 6
        else if (this.second.isDraw(this.first)) result += 3
        return result
    }

    fun part1(input: List<String>): Int = input
        .map {
            it[0] to when (it[2]) {
                'X' -> ROCK
                'Y' -> PAPER
                'Z' -> SCISSORS
                else -> error("Should never happen.")
            }
        }
        .sumOf { it.getScore() }


    fun part2(input: List<String>): Int = input
        .map {
            it[0] to when (it[2]) {
                'X' -> getLosing(it[0])
                'Y' -> getDrawing(it[0])
                'Z' -> getWinning(it[0])
                else -> error("Should never happen.")
            }
        }
        .sumOf { it.getScore() }


    val testInput = readInput("02.test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("02")
    println(part1(input))
    println(part2(input))
}
