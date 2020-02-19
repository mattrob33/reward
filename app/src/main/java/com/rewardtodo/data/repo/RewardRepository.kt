package com.rewardtodo.data.repo

import com.rewardtodo.cache.repo.RewardCacheRepository
import com.rewardtodo.domain.Reward
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RewardRepository @Inject constructor(private val cacheRepo: RewardCacheRepository) {

    fun addReward(reward: Reward) {
        newSingleThreadContext("update_todo").use {
            runBlocking {
                cacheRepo.addReward(reward)
            }
        }
    }

    fun updateReward(reward: Reward) {
        newSingleThreadContext("update_todo").use {
            runBlocking {
                cacheRepo.updateReward(reward)
            }
        }
    }

    fun deleteReward(reward: Reward) {
        newSingleThreadContext("update_todo").use {
            runBlocking {
                cacheRepo.deleteReward(reward)
            }
        }
    }

    fun getAllRewards(): Flow<List<Reward>> = cacheRepo.getAllRewards()

    fun getReward(id: String) = cacheRepo.getReward(id)

}