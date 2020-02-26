package com.rewardtodo.data.repo

import com.rewardtodo.cache.repo.UserCacheRepository
import com.rewardtodo.domain.User
import kotlinx.coroutines.*
import javax.inject.Inject

open class UserRepository @Inject constructor(private val cacheRepo: UserCacheRepository) {

    fun addUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.addUser(user)
        }
    }

    fun deleteUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.deleteUser(user)
        }
    }

    fun deleteUser(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.deleteUser(id)
        }
    }

    fun updateUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.updateUser(user)
        }
    }

    suspend fun getUser(id: String) = cacheRepo.getUser(id)

    fun getUserFlow(id: String) = cacheRepo.getUserFlow(id)

    fun getUserListFlow() = cacheRepo.getUserListFlow()

}