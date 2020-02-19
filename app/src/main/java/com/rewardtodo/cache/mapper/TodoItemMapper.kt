package com.rewardtodo.cache.mapper

import com.rewardtodo.cache.model.TodoItemEntity
import com.rewardtodo.domain.TodoItem

object TodoItemMapper: Mapper<TodoItemEntity, TodoItem> {
    override fun mapToEntity(type: TodoItem): TodoItemEntity {
        return TodoItemEntity(
            type.title,
            type.note,
            type.points,
            type.done,
            type.id
        )
    }

    override fun mapFromEntity(type: TodoItemEntity): TodoItem {
        return TodoItem(
            type.title,
            type.note,
            type.points,
            type.done,
            type.id
        )
    }
}