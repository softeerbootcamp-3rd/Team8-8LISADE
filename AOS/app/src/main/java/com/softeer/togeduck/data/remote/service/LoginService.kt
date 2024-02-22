package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<BaseResponse<String>>
}