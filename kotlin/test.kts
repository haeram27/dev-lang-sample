// how to run - :!kotlin ./%

println("hello")

// file open
val lines = java.io.File("test.kts").readLines()
lines.forEach { println(it) }
