package com.synaxis.android.chatapp.feature.user.domain.use_case

data class UserUseCase(
    val deleteAccount: DeleteAccount,
    val logout: Logout,
    val updateUsername: UpdateUsername,
    val updateFullName: UpdateFullName,
    val updateAvatarUrl: UpdateAvatarUrl,
    val searchUser: SearchUser,
    val getCurrentUser: GetCurrentUser
)