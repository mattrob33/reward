package com.rewardtodo.domain

import java.util.*

data class Reward(
    var title: String,
    var description: String,
    var imagePath: String,
    var points: Int = 1,
    var numPurchases: Int = 0,
    var id: String = UUID.randomUUID().toString()
)