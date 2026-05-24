package com.synaxis.android.chatapp.feature.friends.domain.use_case

data class FriendUseCases(
    val sendFriendRequest: SendFriendRequest,
    val acceptFriendRequest: AcceptFriendRequest,
    val removeFriend: RemoveFriend,
    val rejectFriendRequest: RejectFriendRequest,
    val deleteFriendRequest: DeleteFriendRequest,
    val getFriends: GetFriends,
    val getFriendRequests: GetFriendRequests
)