package com.rewardtodo.cache.db

import androidx.room.*
import com.rewardtodo.cache.db.RewardDb.Companion.REWARDS_TABLE
import com.rewardtodo.cache.db.RewardDb.Companion.TODOS_TABLE
import com.rewardtodo.cache.model.RewardEntity
import com.rewardtodo.cache.model.TodoItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface RewardDao {
    @Insert
    suspend fun insert(reward: RewardEntity)

    @Update
    suspend fun update(vararg reward: RewardEntity)

    @Delete
    suspend fun delete(vararg reward: RewardEntity)

    @Query("SELECT * FROM $REWARDS_TABLE WHERE `id` = :id LIMIT 1")
    fun getReward(id: String): Flow<RewardEntity>

    @Query("SELECT * FROM $REWARDS_TABLE")
    fun getAllRewards(): Flow<List<RewardEntity>>
}