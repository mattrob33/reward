package com.rewardtodo.presentation.mapper

import com.rewardtodo.domain.User
import com.rewardtodo.presentation.models.UserView


object UserMapper: Mapper<UserView, User> {
    override fun mapToView(type: User): UserView {
        return UserView(
            type.name,
            type.points,
            type.id
        )
    }

    override fun mapFromView(type: UserView): User {
        return User(
            type.name,
            type.points,
            type.id
        )
    }
}