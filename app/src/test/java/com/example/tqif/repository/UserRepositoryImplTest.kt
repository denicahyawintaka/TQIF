package com.example.tqif.repository

import app.cash.turbine.test
import com.example.tqif.model.Search
import com.example.tqif.module.ApiService
import net.bytebuddy.utility.RandomString
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class UserRepositoryImplTest {

    @Mock
    lateinit var mockedApiService: ApiService

    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepositoryImpl(apiService = mockedApiService)
    }

    @Test
    suspend fun `findUser should emit list of user when success`() {
        val expectedSearch = Search(emptyList())
        val expectedUsername = RandomString.make(2)
        whenever(mockedApiService.searchUser(expectedUsername)).thenReturn(expectedSearch)
        userRepository.findUsers(expectedUsername).test {
            Assert.assertEquals(expectedSearch, awaitItem())
        }
    }
}
