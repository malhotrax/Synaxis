package com.synaxis.android.chatapp.feature.user.data.remote.api

import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.feature.user.data.remote.dto.GetUserResponseDto
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdateAvatarUrlReq
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdateFullNameReq
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdatePasswordReq
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdateUsernameReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("user")
    suspend fun getCurrentUser(): Response<User>
    @PATCH("user/update/username")
    suspend fun updateUsername(@Body updateUsernameReq: UpdateUsernameReq): Response<MessageResponse>

    @PATCH("user/update/full-name")
    suspend fun updateFullName(@Body updateFullNameReq: UpdateFullNameReq): Response<MessageResponse>

    @PATCH("user/update/avatar-url")
    suspend fun updateAvatarUrl(@Body updateAvatarUrlReq: UpdateAvatarUrlReq): Response<MessageResponse>

    @DELETE("user")
    suspend fun deleteAccount(): Response<MessageResponse>

    @POST("user/logout")
    suspend fun logout(): Response<MessageResponse>

    @PATCH("user/update/password")
    suspend fun updatePassword(@Body updatePasswordReq: UpdatePasswordReq) : Response<MessageResponse>

    @GET("user")
    suspend fun searchUser(
        @Query("query") query: String,
        @Query("limit") limit: Int = 20
    ): Response<GetUserResponseDto>
}