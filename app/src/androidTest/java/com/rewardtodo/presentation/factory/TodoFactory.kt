package com.rewardtodo.presentation.factory

import com.rewardtodo.domain.TodoItem

object TodoFactory {
    fun createTodoItem(): TodoItem {
        return TodoItem("Todo Item", "Item note", 2, false)
    }
}