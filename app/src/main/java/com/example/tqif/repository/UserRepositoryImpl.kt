package com.example.tqif.repository

import com.example.tqif.model.User
import com.example.tqif.module.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {
    override fun findUsers(username: String): Flow<List<User>> {
        return flow {
            val searchResult = apiService.searchUser(username)
            emit(searchResult.items)
        }
    }

    override fun fetchFollowing(username: String): Flow<List<User>> {
        return flow {
            val result = apiService.fetchFollowing(username)
            emit(result)
        }
    }

    override fun fetchFollower(username: String): Flow<List<User>> {
        return flow {
            val result = apiService.fetchFollowers(username)
            emit(result)
        }
    }
}
