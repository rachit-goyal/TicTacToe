package com.learn.tictactoe

/**
created by Rachit on 1/23/2024.
 */
sealed class UserAction {
    data object PlayAgainButtonCLicked : UserAction()
    data class BoardTapped(val cellNo: Int) : UserAction()
}