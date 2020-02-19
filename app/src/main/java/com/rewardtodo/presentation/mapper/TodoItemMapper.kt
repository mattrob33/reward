package com.rewardtodo.presentation.mapper

import com.rewardtodo.domain.TodoItem
import com.rewardtodo.presentation.models.TodoItemView

object TodoItemMapper: Mapper<TodoItemView, TodoItem> {
    override fun mapToView(type: TodoItem): TodoItemView {
        return TodoItemView(
            type.title,
            type.note,
            type.points,
            type.done,
            type.id
        )
    }

    override fun mapFromView(type: TodoItemView): TodoItem {
        return TodoItem(
            type.title,
            type.note,
            type.points,
            type.done,
            type.id
        )
    }
}