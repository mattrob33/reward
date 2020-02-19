package com.rewardtodo.presentation.models

import java.util.*

data class TodoItemView (
    var title: String,
    var note: String,
    var points: Int,
    var done: Boolean,
    var id: String = UUID.randomUUID().toString()
)