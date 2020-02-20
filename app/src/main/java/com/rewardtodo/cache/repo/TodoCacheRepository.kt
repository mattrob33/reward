package com.rewardtodo.cache.repo

import com.rewardtodo.cache.db.TodoItemDao
import com.rewardtodo.cache.mapper.TodoItemMapper
import com.rewardtodo.domain.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class TodoCacheRepository @Inject constructor(private val todoItemDao: TodoItemDao) {

    suspend fun addTodoItem(todo: TodoItem) = todoItemDao.insert( TodoItemMapper.mapToEntity(todo))

    suspend fun updateTodoItem(todo: TodoItem) = todoItemDao.update( TodoItemMapper.mapToEntity(todo) )

    suspend fun deleteTodoItem(todo: TodoItem) = todoItemDao.delete( TodoItemMapper.mapToEntity(todo) )

    suspend fun deleteTodoItem(id: String) = todoItemDao.delete(id)

    fun getAllTodoItems(): Flow<List<TodoItem>> = todoItemDao.getAllTodoItems().map { entityList ->
        entityList.map { entity ->
            TodoItemMapper.mapFromEntity(entity)
        }
    }

    fun getTodoItem(id: String): Flow<TodoItem> = todoItemDao.getTodoItem(id).map {
        TodoItemMapper.mapFromEntity(it)
    }

}