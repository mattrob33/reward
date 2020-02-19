package com.rewardtodo.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import javax.inject.Inject

class TodolistViewModelFactory @Inject constructor (
    private val userRepository: UserRepository,
    private val todoRepository: TodoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodolistViewModel(userRepository, todoRepository) as T
    }

}