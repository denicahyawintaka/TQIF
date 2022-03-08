package com.example.tqif.repository

import com.example.tqif.model.User
import com.example.tqif.module.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {
    override fun invoke(username: String): Flow<List<User>> {
        return flow {
            val searchResult = apiService.searchUser(username)
            emit(searchResult.items)
        }
    }
}
