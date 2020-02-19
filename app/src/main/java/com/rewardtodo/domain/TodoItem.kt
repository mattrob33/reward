package com.rewardtodo.domain

import java.util.*

data class TodoItem(
    var title: String,
    var note: String = "",
    var points: Int = 0,
    var done: Boolean = false,
    var id: String = UUID.randomUUID().toString()
)