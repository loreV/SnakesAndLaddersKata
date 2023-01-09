package game

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    fun `when turn is 3, should determine playable player`() {
        val game = Game(2)
        val gameState = game.play(3)

        assertEquals(1, gameState.playerTurn.id)
    }

    @Test
    fun `when turn is 4, should determine playable player`() {
        val game = Game(2)
        val gameState = game.play(4)

        assertEquals(2, gameState.playerTurn.id)
    }

    @Test
    fun `when turn is 5 and 3 players, should determine playable player`() {
        val game = Game(3)
        val gameState = game.play(5)

        assertEquals(2, gameState.playerTurn.id)
    }

    @Test
    fun `when turn is 10 and 5 players, should determine playable player`() {
        val game = Game(5, emptyMap())
        val gameState = game.play(10)

        assertEquals(5, gameState.playerTurn.id)
    }

    @Test
    fun `should move playable player`() {
        val dice1 : Dice = mockk()
        val dice2 : Dice = mockk()
        val game = Game(2, emptyMap(), listOf(dice1, dice2))
        every { dice1.getValue() } returns 1
        every { dice2.getValue() } returns 3

        val gameState = game.play(1)

        assertEquals(4, gameState.playerTurn.position)
    }

    @Test
    fun `should rethrow die when playable player gets equal number`() {
        val dice1 : Dice = mockk()
        val dice2 : Dice = mockk()
        val game = Game(2, emptyMap(), listOf(dice1, dice2))
        every { dice1.getValue() } returnsMany listOf(1, 2)
        every { dice2.getValue() } returnsMany listOf(1, 3)

        val gameState = game.play(1)

        assertEquals(7, gameState.playerTurn.position)
    }

    @Test
    fun `given a step warp user should be moved to end point`() {
        val dice1 : Dice = mockk()
        val dice2 : Dice = mockk()
        val stepWarp = StepWarp( 5)
        val game = Game(2, mapOf(Pair(3, stepWarp)), listOf(dice1, dice2))
        every { dice1.getValue() } returns 1
        every { dice2.getValue() } returns 2

        val gameState = game.play(1)

        assertEquals(5, gameState.playerTurn.position)
    }

    @Test
    fun `when user exceeding last step, shall move backward`() {
        val dice1 : Dice = mockk()
        val dice2 : Dice = mockk()
        val game = Game(2, emptyMap(), listOf(dice1, dice2), 5)
        every { dice1.getValue() } returns 1
        every { dice2.getValue() } returns 7

        val gameState = game.play(1)

        assertEquals(2, gameState.playerTurn.position)
    }

    @Test
    fun `when user exceeding last step and ending on warp, shall move accordingly`() {
        val dice1 : Dice = mockk()
        val dice2 : Dice = mockk()
        val warpPair = Pair(2, StepWarp(4))
        val game = Game(2, mapOf(warpPair), listOf(dice1, dice2), 5)
        every { dice1.getValue() } returns 1
        every { dice2.getValue() } returns 7

        val gameState = game.play(1)

        assertEquals(4, gameState.playerTurn.position)
    }


}