package game

data class GameState(
    val players: List<Player>,
    val playerTurn: Player,
    val turn: Int,
    val steps: Int
) {
    fun status(): Status =
         if (players.any { it.position == steps }) Status.OVER else Status.RUNNING

    fun getWinner(): List<Player> =
        players.filter { it.position == steps }
}
