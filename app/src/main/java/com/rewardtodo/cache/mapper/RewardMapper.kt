package com.rewardtodo.cache.mapper

import com.rewardtodo.cache.model.RewardEntity
import com.rewardtodo.domain.Reward

object RewardMapper: Mapper<RewardEntity, Reward> {
    override fun mapToEntity(type: Reward): RewardEntity {
        return RewardEntity(
            type.title,
            type.description,
            type.imagePath,
            type.points,
            type.numPurchases,
            type.userId,
            type.id
        )
    }

    override fun mapFromEntity(type: RewardEntity): Reward {
        return Reward(
            type.title,
            type.description,
            type.imagePath,
            type.points,
            type.numPurchases,
            type.userId,
            type.id
        )
    }
}