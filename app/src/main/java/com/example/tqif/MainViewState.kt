package com.example.tqif

import com.example.tqif.model.User

sealed class MainViewState {
    object Idle : MainViewState()
    object Loading : MainViewState()
    data class Success(val users: List<User>) : MainViewState()
    object Error : MainViewState()
}
