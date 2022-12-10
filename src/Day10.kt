fun main() {
    abstract class Instruction
    class NoOp : Instruction()
    class Add(val amount: Int) : Instruction()

    fun parseInstructions(input: List<String>) = input.map {
        when (it) {
            "noop" -> NoOp()
            else -> {
                val (_, amount) = it.split(" ")
                Add(amount.toInt())
            }
        }
    }

    fun part1(input: List<String>): Int {
        var currentStrength = 1
        var cycles = 0
        var result = 0

        fun handleCycle() {
            cycles++
            if ((cycles - 20).rem(40) == 0) {
                result += cycles*currentStrength
            }
        }

        parseInstructions(input).forEach {
            when (it) {
                is NoOp -> handleCycle()
                is Add -> {
                    handleCycle()
                    handleCycle()
                    currentStrength += it.amount
                }
            }
        }

        return result
    }

    fun part2(input: List<String>) = buildString {
        var x = 1
        var cycles = 0

        fun handleCycle() {
            val localX = cycles.rem(40)
            append(if (localX in (x-1)..(x+1)) "#" else ".")
            cycles++
            if (cycles.rem(40) == 0) {
                append("\n")
            }
        }

        parseInstructions(input).forEach {
            when (it) {
                is NoOp -> handleCycle()
                is Add -> {
                    handleCycle()
                    handleCycle()
                    x += it.amount
                }
            }
        }
    }

    val testInput = readInput("10.test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == """
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
    """.trimIndent()+"\n")

    val input = readInput("10")
    println(part1(input))
    println(part2(input))
}
