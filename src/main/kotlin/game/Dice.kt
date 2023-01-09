package game

import kotlin.random.Random

class Dice {
    fun getValue() = Random.nextInt(0, 6)
}