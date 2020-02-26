package com.rewardtodo.presentation.models

import java.util.*

data class RewardView (
    var title: String,
    var description: String,
    var imagePath: String,
    var points: Int = 1,
    var numPurchases: Int = 0,
    var userId: String = "",
    var id: String = UUID.randomUUID().toString()
)