fun main() {
    data class MoveInstruction(val amount: Int, val from: Int, val to: Int)
    data class InputData(val stacks: Array<ArrayDeque<Char>>, val moves: List<MoveInstruction>)

    fun parseInput(input: String): InputData {
        fun parseStacks(data: String): Array<ArrayDeque<Char>> {
            val lines = data.lines().reversed()
            val numStacks = lines[0].replace(" ", "").length
            val stacks = Array(numStacks) { ArrayDeque<Char>() }

            lines.drop(1).forEach { line ->
                (0 until numStacks).forEach { index ->
                    val char = line.getOrElse(index*4 + 1) { ' ' }
                    if (char != ' ') {
                        stacks[index].addLast(char)
                    }
                }
            }

            return stacks
        }

        fun parseMoves(data: String): List<MoveInstruction> {
            val matcher = """move (\d+) from (\d+) to (\d+)""".toRegex();
            return data.trim().lines().map {
                val result = matcher.find(it.trim()) ?: error("parsing failed")
                val (_, amount, from, to) = result.groupValues
                MoveInstruction(amount.toInt(), from.toInt() - 1, to.toInt() - 1)
            }
        }

        val (stacksInput, instructionsInput) = input.replace("\r", "").split("\n\n")
        return InputData(parseStacks(stacksInput), parseMoves(instructionsInput));
    }

    fun part1(input: String): String {
        val (stacks, moves) = parseInput(input)
        moves.forEach { move ->
            repeat(move.amount) {
                stacks[move.to].addLast(stacks[move.from].removeLast())
            }
        }
        return stacks.map { it.last() }.joinToString("")
    }

    fun part2(input: String): String {
        val (stacks, moves) = parseInput(input)
        moves.forEach { move ->
            (1..move.amount)
                .map { stacks[move.from].removeLast() }
                .reversed()
                .forEach { stacks[move.to].addLast(it) }
        }
        return stacks.map { it.last() }.joinToString("")
    }

    val testInput = readInputFull("05.test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputFull("05")
    println(part1(input))
    println(part2(input))
}
