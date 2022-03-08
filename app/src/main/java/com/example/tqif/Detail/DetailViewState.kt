package com.example.tqif.Detail

import com.example.tqif.model.User

sealed class DetailViewState {
    object Idle : DetailViewState()
    object Loading : DetailViewState()
    data class Success(val following: List<User>, val follower: List<User>) : DetailViewState()
    object Error : DetailViewState()
}
