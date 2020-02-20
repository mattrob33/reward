package com.rewardtodo.cache.db

import androidx.room.*
import com.rewardtodo.cache.db.RewardDb.Companion.TODOS_TABLE
import com.rewardtodo.cache.model.TodoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Insert
    suspend fun insert(item: TodoItemEntity)

    @Update
    suspend fun update(vararg item: TodoItemEntity)

    @Delete
    suspend fun delete(vararg item: TodoItemEntity)

    @Query("DELETE FROM $TODOS_TABLE WHERE `id` = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM $TODOS_TABLE WHERE `id` = :id LIMIT 1")
    fun getTodoItem(id: String): Flow<TodoItemEntity>

    @Query("SELECT * FROM $TODOS_TABLE")
    fun getAllTodoItems(): Flow<List<TodoItemEntity>>
}