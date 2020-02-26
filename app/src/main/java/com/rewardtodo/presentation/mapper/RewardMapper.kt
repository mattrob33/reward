package com.rewardtodo.presentation.mapper

import com.rewardtodo.domain.Reward
import com.rewardtodo.presentation.models.RewardView

object RewardMapper: Mapper<RewardView, Reward> {
    override fun mapToView(type: Reward): RewardView {
        return RewardView(
            type.title,
            type.description,
            type.imagePath,
            type.points,
            type.numPurchases,
            type.id
        )
    }

    override fun mapFromView(type: RewardView): Reward {
        return Reward(
            type.title,
            type.description,
            type.imagePath,
            type.points,
            type.numPurchases,
            type.id
        )
    }
}