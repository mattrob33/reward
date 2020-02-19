package com.rewardtodo.domain

import java.util.*

data class User(
    var name: String = "",
    var points: Int = 0,
    var id: String = UUID.randomUUID().toString()
)