package com.rewardtodo.data.repo

import com.rewardtodo.cache.repo.RewardCacheRepository
import com.rewardtodo.domain.Reward
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RewardRepository @Inject constructor(private val cacheRepo: RewardCacheRepository) {

    fun addReward(reward: Reward) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.addReward(reward)
        }
    }

    fun updateReward(reward: Reward) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.updateReward(reward)
        }
    }

    fun deleteReward(reward: Reward) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.deleteReward(reward)
        }
    }

    fun getAllRewardsForUser(userId: String): Flow<List<Reward>> = cacheRepo.getAllRewardsForUser(userId)

    fun getReward(id: String) = cacheRepo.getReward(id)

}