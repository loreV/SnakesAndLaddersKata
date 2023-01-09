package game

enum class Status {
    RUNNING, OVER
}

class Game constructor(players: Int,
                       private val stepWarps: Map<Int, StepWarp> = emptyMap(),
                       private val die : List<Dice> = listOf(Dice(), Dice()),
                       private val stepsNumber : Int = 30) {

    private val players: List<Player>

    init {
        this.players = (1..players).map { Player(it, 0) }
    }

    fun play(turn: Int): GameState {
        val player: Player = determinePlayer(turn)
        do {
            val diceThrowValue = throwDice()
            val position = calculatePlayerPosition(player.position, diceThrowValue)
            player.move(position)
        } while (isRethrow(diceThrowValue))

        println("Player with ID ${player.id}, moved to ${player.position}!")

        return GameState(players, player, turn, stepsNumber)
    }

    private fun calculatePlayerPosition(playerPosition: Int, diceThrowValue: List<Int>): Int {
        val locationAfterMovement = getPosition(playerPosition, diceThrowValue)
        return stepWarps.getOrDefault(locationAfterMovement, StepWarp(locationAfterMovement)).to
    }

    private fun getPosition(playerPosition: Int, diceThrowValue: List<Int>) : Int {
        val preliminaryPosition = playerPosition + diceThrowValue.sum()
        return if (preliminaryPosition > stepsNumber)
            calculateExcessSteps(preliminaryPosition) else preliminaryPosition
    }

    private fun calculateExcessSteps(preliminaryPosition: Int) =
        stepsNumber - (preliminaryPosition - stepsNumber)

    private fun throwDice() = die.map { it.getValue() }

    private fun isRethrow(diceThrowValue: List<Int>) =
        diceThrowValue.all { it == diceThrowValue.first() }

    private fun determinePlayer(turn: Int): Player {
        if (turn < players.size) return players[turn - 1]
        val position = (turn % players.size)
        val indexOffset = (if (position == 0) players.size else position) - 1
        return players[indexOffset]
    }

}