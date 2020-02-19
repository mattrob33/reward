package com.rewardtodo.data.repo

import com.rewardtodo.cache.repo.UserCacheRepository
import com.rewardtodo.domain.User
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserRepository @Inject constructor(private val cacheRepo: UserCacheRepository) {

    fun addUser(user: User) {
        newSingleThreadContext("update_todo").use {
            runBlocking {
                cacheRepo.addUser(user)
            }
        }
    }

    fun deleteUser(user: User) {
        newSingleThreadContext("update_todo").use {
            runBlocking {
                cacheRepo.deleteUser(user)
            }
        }
    }

    fun updateUser(user: User) {
        newSingleThreadContext("update_todo").use {
            runBlocking {
                cacheRepo.updateUser(user)
            }
        }
    }

    suspend fun getUser() = cacheRepo.getUser()

    fun getUserFlow() = cacheRepo.getUserFlow()

}