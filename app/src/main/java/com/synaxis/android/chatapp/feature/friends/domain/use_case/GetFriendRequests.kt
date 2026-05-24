package com.synaxis.android.chatapp.feature.friends.domain.use_case

import com.synaxis.android.chatapp.core.common.network.GetFriendRequestsResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.friends.domain.repository.FriendsRepository
import javax.inject.Inject

class GetFriendRequests @Inject constructor(
    private  val friendsRepository: FriendsRepository
) {
    suspend operator fun invoke(): ApiResult<GetFriendRequestsResponse> {
        return friendsRepository.getFriendRequests()
    }
}