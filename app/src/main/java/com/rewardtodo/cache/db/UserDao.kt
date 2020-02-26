package com.rewardtodo.cache.db

import androidx.room.*
import com.rewardtodo.cache.db.RewardDb.Companion.TODOS_TABLE
import com.rewardtodo.cache.db.RewardDb.Companion.USER_TABLE
import com.rewardtodo.cache.model.TodoItemEntity
import com.rewardtodo.cache.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(vararg user: UserEntity)

    @Delete
    suspend fun delete(vararg user: UserEntity)

    @Query("DELETE FROM $USER_TABLE WHERE `id` = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM $USER_TABLE WHERE `id` = :id LIMIT 1")
    suspend fun getUser(id: String): UserEntity

    @Query("SELECT * FROM $USER_TABLE WHERE `id` = :id")
    fun getUserFlow(id: String): Flow<UserEntity>

    @Query("SELECT * FROM $USER_TABLE")
    fun getUserListFlow(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM $USER_TABLE")
    suspend fun numUsers(): Int
}