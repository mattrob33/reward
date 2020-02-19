package com.rewardtodo.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rewardtodo.cache.db.RewardDb.Companion.TODOS_TABLE
import com.rewardtodo.cache.db.RewardDb.Companion.USER_TABLE
import java.util.*

@Entity(tableName = USER_TABLE)
data class UserEntity(
    var name: String,
    var points: Int = 0,
    @PrimaryKey var id: String = UUID.randomUUID().toString()
)