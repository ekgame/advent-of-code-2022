fun main() {
    fun String.addIndent(indent: Int) = lines().joinToString("\n") { " ".repeat(indent) + it }

    abstract class FileSystemEntry(val name: String) {
        abstract val size: Int
    }

    class Directory(name: String, val parent: Directory? = null) : FileSystemEntry(name) {
        override val size: Int by lazy { content.sumOf { it.size } }

        val content: MutableList<FileSystemEntry> = mutableListOf()

        fun createChildDirectory(name: String) = Directory(name, this).also { this.content.add(it) }

        fun getOrCreateChildDirectory(name: String): Directory = content
            .filterIsInstance<Directory>()
            .firstOrNull { it.name == name }
            ?: createChildDirectory(name)

        fun flattenDirectories(): List<Directory> =  buildList {
            content.filterIsInstance<Directory>().forEach {
                add(it)
                addAll(it.flattenDirectories())
            }
        }

        override fun toString(): String = buildString {
            append("- $name (dir)\n")
            append(content.flatMap { it.toString().lines().map { it.addIndent(2) } }.joinToString("\n"))
        }
    }

    class File(name: String, override val size: Int, val parent: Directory) : FileSystemEntry(name) {
        override fun toString(): String = "- $name (file, size=$size)"
    }

    fun parseFileSystem(input: List<String>): Directory {
        val rootDirectory = Directory("root")
        var currentDirectory = rootDirectory

        var cursor = 0
        val hasMoreLines = { cursor < input.size }

        data class DirectoryEntry(val name: String, val isFile: Boolean, val size: Int)
        fun parseDirectoryEntries() =
            sequence {
                while (hasMoreLines() && !input[cursor].startsWith("$")) {
                    yield(input[cursor++])
                }
            }
            .map {
                val (size, name) = it.split(" ")
                when (size) {
                    "dir" -> DirectoryEntry(name, false, 0)
                    else -> DirectoryEntry(name, true, size.toInt())
                }
            }

        while (hasMoreLines()) {
            val command = input[cursor++]
            when {
                command.startsWith("$ cd ") -> {
                    currentDirectory = when (val name = command.substring(5)) {
                        "/" -> rootDirectory
                        ".." -> currentDirectory.parent ?: error("going back from root directory")
                        else -> currentDirectory.getOrCreateChildDirectory(name)
                    }
                }
                command.startsWith("$ ls") -> {
                    parseDirectoryEntries().forEach {
                        if (it.isFile) {
                            val file = File(it.name, it.size, currentDirectory)
                            currentDirectory.content.add(file)
                        } else {
                            currentDirectory.createChildDirectory(it.name)
                        }
                    }
                }
                else -> error("Expected command, but got: $command")
            }
        }

        return rootDirectory
    }

    fun part1(input: List<String>): Int = parseFileSystem(input)
        .flattenDirectories()
        .filter { it.size <= 100000 }
        .sumOf { it.size }

    fun part2(input: List<String>): Int {
        val root = parseFileSystem(input)
        return root.flattenDirectories()
            .sortedBy { it.size }
            .first { 70_000_000 - root.size + it.size >= 30_000_000 }
            .size
    }

    val testInput = readInput("07.test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("07")
    println(part1(input))
    println(part2(input))
}
