import game.Dice
import game.Game
import game.Status
import game.StepWarp

fun main(args: Array<String>) {
    println("Snakes&Ladders, Kotlin console edition")

    val game = Game(
        2,
        mapOf(Pair(9, StepWarp(5))),
        listOf(Dice(), Dice()),
        30
    )
    readln()

    val count = generateSequence(1) {
        val turn = it + 1
        println("Hit any key for next turn: #$turn")
        readln()
        turn
    }
        .takeWhile { game.play(it).status() == Status.RUNNING }
        .count()

    println("Game ended in #$count turns")
}

