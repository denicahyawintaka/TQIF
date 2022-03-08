package com.example.tqif.repository

import com.example.tqif.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun findUsers(username: String): Flow<List<User>>
    fun fetchFollowing(username: String): Flow<List<User>>
    fun fetchFollower(username: String): Flow<List<User>>
}
