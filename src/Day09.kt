import kotlin.math.abs

fun main() {
    data class Point(val x: Int, val y: Int) {
        fun isTouching(other: Point) = other.x in (x-1)..(x+1) && other.y in (y-1)..(y+1)
        fun add(other: Point) = Point(other.x + x, other.y + y)
    }

    data class MoveInstruction(val vector: Point, val magnitude: Int)

    class ChainSimulation(pieces: Int) {
        val pieces = Array(pieces) { Point(0, 0) }

        val head: Point
            get() = pieces.first()

        val tail: Point
            get() = pieces.last()

        fun moveHead(vector: Point) {
            pieces[0] = head.add(vector)
            moveChain(1)
        }

        fun moveChain(index: Int) {
            if (index >= pieces.size) {
                return
            }
            val head = pieces[index - 1]
            val tail = pieces[index]
            if (head.isTouching(tail)) {
                return
            }

            pieces[index] = when {
                head.x == tail.x -> Point(tail.x, (head.y + tail.y)/2)
                head.y == tail.y -> Point((head.x + tail.x)/2, tail.y)
                abs(head.x - tail.x) == abs(head.y - tail.y) -> Point((head.x + tail.x)/2, (head.y + tail.y)/2)
                abs(head.x - tail.x) > 1 -> Point((head.x + tail.x)/2, head.y)
                abs(head.y - tail.y) > 1 -> Point(head.x, (head.y + tail.y)/2)
                else -> error("Invalid state")
            }

            moveChain(index + 1)
        }

        fun dump() {
            val x1 = -11//pieces.minOf { it.x }
            val y1 = -5//pieces.minOf { it.y }
            val x2 = 15//pieces.maxOf { it.x }
            val y2 = 15//pieces.maxOf { it.y }
            (y1..y2).reversed().forEach { y ->
                (x1..x2).forEach { x ->
                    val index = pieces.indexOfFirst { it.x == x && it.y == y } + 1
                    print(when (index) {
                        0 -> "."
                        1 -> "H"
                        else -> index - 1
                    })
                }
                print("\n")
            }
            println("--------------")
        }
    }

    fun parseInstructions(input: List<String>) = input.map {
        val (direction, magnitude) = it.split(" ")
        when (direction) {
            "L" -> MoveInstruction(Point(-1, 0), magnitude.toInt())
            "R" -> MoveInstruction(Point(1, 0), magnitude.toInt())
            "U" -> MoveInstruction(Point(0, 1), magnitude.toInt())
            "D" -> MoveInstruction(Point(0, -1), magnitude.toInt())
            else -> error("invalid direction")
        }
    }

    fun solve(input: List<String>, parts: Int): Int {
        val simulation = ChainSimulation(parts)
        val visited = mutableSetOf(simulation.tail)
        parseInstructions(input).forEach { move ->
            repeat(move.magnitude) {
                simulation.moveHead(move.vector)
                visited.add(simulation.tail)
            }
//            simulation.dump()
        }

        return visited.size
    }

    fun part1(input: List<String>) = solve(input, 2)

    fun part2(input: List<String>) = solve(input, 10)

    val testInput = readInput("09.test")
    val testInput2 = readInput("09.2.test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)
    check(part2(testInput2) == 36)

    val input = readInput("09")
    println(part1(input))
    println(part2(input))
}
