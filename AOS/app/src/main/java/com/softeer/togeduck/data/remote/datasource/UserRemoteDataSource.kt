package com.softeer.togeduck.data.remote.datasource

import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.request.LoginRequest
import com.softeer.togeduck.data.remote.service.UserService
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun login(
        loginRequest: LoginRequest
    ): Response<String> {
        return userService.login(loginRequest)
    }
}