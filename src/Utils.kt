import java.io.File

fun readInput(name: String) = File("inputs", "$name.txt")
    .readLines()

fun readInputFull(name: String) = File("inputs", "$name.txt")
    .readText()