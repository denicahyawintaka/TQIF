package com.example.tqif.repository

import com.example.tqif.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    operator fun invoke(username: String): Flow<List<User>>
}
