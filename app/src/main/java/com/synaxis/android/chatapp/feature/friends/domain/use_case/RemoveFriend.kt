package com.synaxis.android.chatapp.feature.friends.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.friends.domain.repository.FriendsRepository
import javax.inject.Inject

class RemoveFriend @Inject constructor(
    private val friendsRepository: FriendsRepository
) {
    suspend operator fun invoke(friendId: String) : ApiResult<MessageResponse> {
        return friendsRepository.removeFriend(friendId)
    }
}