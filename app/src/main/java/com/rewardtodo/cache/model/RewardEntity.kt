package com.rewardtodo.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rewardtodo.cache.db.RewardDb.Companion.REWARDS_TABLE
import java.util.*

@Entity(tableName = REWARDS_TABLE)
data class RewardEntity(
    var title: String,
    var description: String,
    var imagePath: String,
    var points: Int = 1,
    var numPurchases: Int = 0,
    @PrimaryKey var id: String = UUID.randomUUID().toString()
)