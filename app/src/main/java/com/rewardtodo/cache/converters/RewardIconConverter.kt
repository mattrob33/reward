package com.rewardtodo.cache.converters

import androidx.room.TypeConverter
import com.rewardtodo.domain.Reward

class RewardIconConverter {
    @TypeConverter
    fun rewardIconToInt(icon: Reward.Icon): Int {
        return icon.ordinal
    }

    @TypeConverter
    fun fromInt(value: Int): Reward.Icon {
        return Reward.Icon.values()[value]
    }
}