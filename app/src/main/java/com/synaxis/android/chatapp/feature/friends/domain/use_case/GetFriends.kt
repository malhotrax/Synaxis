package com.synaxis.android.chatapp.feature.friends.domain.use_case

import androidx.paging.PagingData
import com.synaxis.android.chatapp.core.common.network.GetFriendResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import com.synaxis.android.chatapp.feature.friends.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFriends @Inject constructor(
    private val friendsRepository: FriendsRepository
) {
    operator fun invoke(): Flow<PagingData<Friend>> {
        return friendsRepository.getFriends()
    }
}