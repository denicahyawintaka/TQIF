package com.example.tqif

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tqif.Detail.DetailViewState
import com.example.tqif.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = mutableStateOf<MainViewState>(MainViewState.Idle)
    val mainViewState: State<MainViewState> = _viewState

    private val _detailViewState = mutableStateOf<DetailViewState>(DetailViewState.Idle)
    val detailViewState: State<DetailViewState> = _detailViewState

    fun findUser(username: String) {
        viewModelScope.launch {
            userRepository.findUsers(username)
                .onStart {
                    _viewState.value = MainViewState.Loading
                }
                .catch { exception ->
                    _viewState.value = MainViewState.Error
                }
                .collect { users ->
                    if (users.isNotEmpty()) {
                        _viewState.value = MainViewState.Success(users = users)
                    } else {
                        _viewState.value = MainViewState.Idle
                    }
                }
        }
    }

    fun loadDetail(username: String) {
        viewModelScope.launch {
            val followingFlow = userRepository.fetchFollowing(username)
            val followerFlow = userRepository.fetchFollower(username)
            followingFlow.zip(followerFlow) { following, follower ->
                _detailViewState.value =
                    DetailViewState.Success(following = following, follower = follower)
            }
                .onStart {
                    _viewState.value = MainViewState.Loading
                }
                .catch { exception ->
                    _viewState.value = MainViewState.Error
                }
                .collect()
        }
    }

}
