package game

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

internal class GameStateTest {

    @Test
    fun `when no player has reached final step, should return status running`() {
        val player1 = Player(0, 21)
        val player2 = Player(1, 25)
        val gameState = GameState(players = listOf(player1, player2), player1, 3, 30)
        assertEquals(Status.RUNNING, gameState.status())
    }

    @Test
    fun `when first player has reached final step, should return status over`() {
        val player1 = Player(0, 30)
        val player2 = Player(1, 25)
        val gameState = GameState(players = listOf(player1, player2), player1, 3, 30)
        assertEquals(Status.OVER, gameState.status())
    }

    @Test
    fun `when second player has reached final step, get winner should return player`() {
        val player1 = Player(0, 22)
        val player2 = Player(1, 30)
        val gameState = GameState(players = listOf(player1, player2), player2, 3, 30)
        assertContains(gameState.getWinner(), player2)
    }

    @Test
    fun `when no player has reached final step, get winner should return no player`() {
        val player1 = Player(0, 22)
        val player2 = Player(1, 26)
        val gameState = GameState(players = listOf(player1, player2), player2, 3, 30)
        assertTrue(gameState.getWinner().isEmpty())
    }
}