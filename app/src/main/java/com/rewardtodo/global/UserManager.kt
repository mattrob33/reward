package com.rewardtodo.global

import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class UserManager @Inject constructor(
    private val userRepo: UserRepository,
    private val prefs: PreferencesHelper
) {
    private var initUser = User(name = "Me") // used as a placeholder until actual flow is retrieved

    var currentUserFlow: Flow<User> = flow { initUser }
        private set

    var userListFlow = userRepo.getUserListFlow()

    init {
        if (prefs.currentUserId.isEmpty()) {
            prefs.currentUserId = initUser.id
            userRepo.addUser(initUser)
            restartUserFlow(initUser.id)
        }
        else
            restartUserFlow(prefs.currentUserId)
    }

    private fun restartUserFlow(userId: String) {
        currentUserFlow = userRepo.getUserFlow(userId)
    }

    fun changeCurrentUser(userId: String) {
        prefs.currentUserId = userId
        restartUserFlow(userId)
    }

}