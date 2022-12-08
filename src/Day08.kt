fun main() {
    data class Tree(val size: Int, var visible: Boolean = false)
    data class TreeGrid(val trees: Array<Array<Tree>>) {
        val height = trees.size
        val width = trees.first().size
        fun getHorizontalSequence(index: Int) = trees[index].toList()
        fun getVerticalSequence(index: Int) = (0 until height).map { trees[it][index] }

        fun processLineVisibility(sequence: Iterable<Tree>) {
            var currentSize = -1
            sequence.forEach {
                if (it.size > currentSize) {
                    it.visible = true
                    currentSize = it.size
                }
            }
        }

        fun processVisibility() {
            repeat(height) {
                processLineVisibility(getHorizontalSequence(it))
                processLineVisibility(getHorizontalSequence(it).reversed())
            }

            repeat(width) {
                processLineVisibility(getVerticalSequence(it))
                processLineVisibility(getVerticalSequence(it).reversed())
            }
        }

        fun getScenicScore(tower: Tree, sequence: List<Tree>): Int {
            if (sequence.isEmpty()) return 0
            val visibleTrees = sequence.takeWhile { it.size < tower.size }.count()
            return (visibleTrees + 1).coerceAtMost(sequence.size)
        }

        fun getScenicScoreAt(x: Int, y: Int): Int {
            if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                return 0
            }

            val tower = trees[y][x]
            val horizontal = getHorizontalSequence(y)
            val vertical = getVerticalSequence(x)
            val leftScore = getScenicScore(tower, horizontal.subList(0, x).reversed())
            val rightScore = getScenicScore(tower, horizontal.subList(x + 1, width))
            val upScore = getScenicScore(tower, vertical.subList(0, y).reversed())
            val downScore = getScenicScore(tower, vertical.subList(y + 1, height))
            return leftScore * rightScore * upScore * downScore
        }
    }

    fun parseTreeGrid(input: List<String>) = TreeGrid(Array(input.size) { line -> input[line].map { Tree(it.digitToInt()) }.toTypedArray() })

    fun <T> cartesianSequence(first: List<T>, second: List<T>) = sequence {
        first.forEach { x ->
            second.forEach { y ->
                yield(x to y)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val grid = parseTreeGrid(input)
        grid.processVisibility()
        return grid.trees.flatten().count { it.visible }
    }

    fun part2(input: List<String>): Int {
        val grid = parseTreeGrid(input)
        return cartesianSequence((0 until grid.width).toList(), (0 until grid.height).toList())
            .maxOf { (x, y) -> grid.getScenicScoreAt(x, y) }
    }

    val testInput = readInput("08.test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("08")
    println(part1(input))
    println(part2(input))
}
