package com.example.tqif

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tqif.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = mutableStateOf<MainViewState>(MainViewState.Idle)
    val mainViewState: State<MainViewState> = _viewState

    fun findUser(username: String) {
        viewModelScope.launch {
            userRepository(username)
                .onStart {
                    _viewState.value = MainViewState.Loading
                }
                .catch {
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

}
