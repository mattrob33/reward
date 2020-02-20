package com.rewardtodo.presentation.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.TodoItem
import com.rewardtodo.domain.User
import com.rewardtodo.global.UserManager
import com.rewardtodo.presentation.mapper.TodoItemMapper
import com.rewardtodo.presentation.models.TodoItemView
import com.rewardtodo.util.Event
import com.rewardtodo.util.cancelIfActive
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodolistViewModel (
    private val userManager: UserManager,
    private val userRepo: UserRepository,
    private val todoRepo: TodoRepository,
    private val prefs: PreferencesHelper
): ViewModel() {

    private var getTodoItemsJob: Job? = null

    private val _items = MutableLiveData<List<TodoItemView>>()
    val items = _items

    private val newItemPointsOptions = listOf(1, 2, 4, 8)
    private var newItemPointsIndex = 0

    private val _newItemPoints = MutableLiveData<Int>()
    val newItemPoints = _newItemPoints

    val selectedItems = mutableSetOf<String>()

    private val _numItemsSelected = MutableLiveData<Event<Int>>()
    val numItemsSelected: LiveData<Event<Int>>
        get() = _numItemsSelected

    var user = User()

    init {
        viewModelScope.launch {
            userManager.currentUser.collect {
                user = it
                updateForUser()
            }
        }
    }

    private fun updateForUser() {
//        getTodoItems()    // TODO: once items are user-specific
    }

    fun getTodoItems() {
        getTodoItemsJob?.cancelIfActive()

        getTodoItemsJob = viewModelScope.launch {
            todoRepo.getAllTodoItems().collect { itemList ->
                var filteredList = itemList.map { TodoItemMapper.mapToView(it) }
                if (!prefs.showCompletedTodoItems)
                    filteredList = filteredList.filter { !it.done }
                _items.value = filteredList
            }
        }
    }

    fun addTodoItem(newItem: TodoItem): Job {
        return todoRepo.addTodoItem(newItem)
    }

    fun toggleItem(item: TodoItem) {
        item.done = !item.done
        todoRepo.updateTodoItem(item)

        user.points +=
            if (item.done)
                item.points
            else
                -item.points
        userRepo.updateUser(user)
    }

    fun toggleHideCompletedItems() {
        prefs.showCompletedTodoItems = !prefs.showCompletedTodoItems
        getTodoItems()
    }

    fun addItemToSelectedList(todoItem: TodoItem) {
        selectedItems.add(todoItem.id)
        _numItemsSelected.value = Event(selectedItems.size)
    }

    fun removeItemFromSelectedList(todoItem: TodoItem) {
        selectedItems.remove(todoItem.id)
        if (selectedItems.isEmpty())
            _numItemsSelected.value = Event(0)
    }

    fun deleteSelectedItems() {
        for (itemId in selectedItems)
            todoRepo.deleteTodoItem(itemId)

        selectedItems.clear()
        _numItemsSelected.value = Event(0)
    }

    fun cycleNewItemPoints() {
        newItemPointsIndex++
        if (newItemPointsIndex >= newItemPointsOptions.size)
            newItemPointsIndex = 0
        _newItemPoints.value = newItemPointsOptions[newItemPointsIndex]
    }
}