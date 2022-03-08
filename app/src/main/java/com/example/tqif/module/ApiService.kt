package com.example.tqif.module

import com.example.tqif.model.Search
import com.example.tqif.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?")
    suspend fun searchUser(@Query("q") username: String): Search

    /* @GET("users/{username}")
     fun fetchUserDetail(@Path("username") username: String): Single<UserDetail>*/

    @GET("users/{username}/followers")
    suspend fun fetchFollowers(@Path("username") username: String): List<User>

    @GET("users/{username}/following")
    suspend fun fetchFollowing(@Path("username") username: String): List<User>
}
