package com.rewardtodo.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rewardtodo.cache.db.RewardDb.Companion.REWARDS_TABLE
import com.rewardtodo.domain.Reward
import java.util.*

@Entity(tableName = REWARDS_TABLE)
data class RewardEntity(
    var title: String,
    var description: String,
    var icon: Reward.Icon,
    var imagePath: String,
    var points: Int = 1,
    var numPurchases: Int = 0,
    var userId: String = "",
    @PrimaryKey var id: String = UUID.randomUUID().toString()
)