package com.rewardtodo.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.global.UserManager
import javax.inject.Inject

class TodolistViewModelFactory @Inject constructor (
    private val userManager: UserManager,
    private val userRepo: UserRepository,
    private val todoRepo: TodoRepository,
    private val prefs: PreferencesHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodolistViewModel(userManager, userRepo, todoRepo, prefs) as T
    }

}