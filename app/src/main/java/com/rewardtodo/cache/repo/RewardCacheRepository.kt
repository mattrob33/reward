package com.rewardtodo.cache.repo

import com.rewardtodo.cache.db.RewardDao
import com.rewardtodo.cache.mapper.RewardMapper
import com.rewardtodo.domain.Reward
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RewardCacheRepository @Inject constructor(private val rewardDao: RewardDao) {

    suspend fun addReward(reward: Reward) = rewardDao.insert( RewardMapper.mapToEntity(reward))

    suspend fun updateReward(reward: Reward) = rewardDao.update( RewardMapper.mapToEntity(reward) )

    suspend fun deleteReward(reward: Reward) = rewardDao.delete( RewardMapper.mapToEntity(reward) )

    fun getAllRewardsForUser(userId: String): Flow<List<Reward>> = rewardDao.getAllRewardsForUser(userId).map { entityList ->
        entityList.map { entity ->
            RewardMapper.mapFromEntity(entity)
        }
    }

    fun getReward(id: String): Flow<Reward?> = rewardDao.getReward(id).map {
        if (it == null)
            null
        else
            RewardMapper.mapFromEntity(it)
    }
}