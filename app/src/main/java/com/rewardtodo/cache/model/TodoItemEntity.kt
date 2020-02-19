package com.rewardtodo.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rewardtodo.cache.db.RewardDb.Companion.TODOS_TABLE
import com.rewardtodo.cache.db.RewardDb.Companion.USER_TABLE
import java.util.*

@Entity(tableName = TODOS_TABLE)
data class TodoItemEntity(
    var title: String,
    var note: String = "",
    var points: Int = 0,
    var done: Boolean = false,
    @PrimaryKey var id: String = UUID.randomUUID().toString()
)