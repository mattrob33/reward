package com.rewardtodo.util

import kotlinx.coroutines.Job

fun Job.cancelIfActive() {
    if (isActive) {
        cancel()
    }
}