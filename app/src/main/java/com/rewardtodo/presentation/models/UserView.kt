package com.rewardtodo.presentation.models

import java.util.*

data class UserView (
    var name: String = "",
    var points: Int = 0,
    var id: String = UUID.randomUUID().toString()
)