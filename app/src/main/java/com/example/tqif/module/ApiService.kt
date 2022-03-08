package com.example.tqif.module

import com.example.tqif.model.Search
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?")
    suspend fun searchUser(@Query("q") username: String): Search

    /* @GET("users/{username}")
     fun fetchUserDetail(@Path("username") username: String): Single<UserDetail>

     @GET("users/{username}/followers")
     fun fetchFollowers(@Path("username") username: String): Single<List<User>>

     @GET("users/{username}/following")
     fun fetchFollowing(@Path("username") username: String): Single<List<User>>*/
}
