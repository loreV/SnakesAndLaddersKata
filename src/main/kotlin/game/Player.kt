package game

class Player constructor(val id : Int,
                         var position: Int = 0) {
    fun move(to: Int) {
        position = to
    }
}