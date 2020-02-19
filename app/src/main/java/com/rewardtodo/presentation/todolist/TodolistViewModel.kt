package com.rewardtodo.presentation.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.TodoItem
import com.rewardtodo.presentation.mapper.TodoItemMapper
import com.rewardtodo.presentation.models.TodoItemView
import com.rewardtodo.util.cancelIfActive
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.produceIn

class TodolistViewModel (
    private val userRepository: UserRepository,
    private val todoRepository: TodoRepository
): ViewModel() {

    private var getTodoItemsJob: Job? = null

    private val _items = MutableLiveData<List<TodoItemView>>()
    val items = _items

    fun getTodoItems() {
        getTodoItemsJob?.cancelIfActive()

        getTodoItemsJob = viewModelScope.launch {
            withContext(Dispatchers.Main) {
                todoRepository.getAllTodoItems().collect { itemList ->
                    _items.value = itemList.map { TodoItemMapper.mapToView(it) }.filter { !it.done }
                }
            }
        }
    }

    fun addTodoItem(newItem: TodoItem): Job {
        return todoRepository.addTodoItem(newItem)
    }
}