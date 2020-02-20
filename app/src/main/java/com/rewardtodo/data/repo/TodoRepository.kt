package com.rewardtodo.data.repo

import com.rewardtodo.cache.repo.TodoCacheRepository
import com.rewardtodo.domain.TodoItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class TodoRepository @Inject constructor(private val cacheRepo: TodoCacheRepository) {

    fun addTodoItem(todo: TodoItem): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.addTodoItem(todo)
        }
    }

    fun updateTodoItem(todo: TodoItem) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.updateTodoItem(todo)
        }
    }

    fun deleteTodoItem(todo: TodoItem) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.deleteTodoItem(todo)
        }
    }

    fun deleteTodoItem(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            cacheRepo.deleteTodoItem(id)
        }
    }

    fun getAllTodoItems(): Flow<List<TodoItem>> = cacheRepo.getAllTodoItems()

    fun getTodoItem(id: String) = cacheRepo.getTodoItem(id)

}