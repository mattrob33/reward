package com.rewardtodo.cache.mapper

import com.rewardtodo.cache.model.UserEntity
import com.rewardtodo.domain.User

object UserMapper: Mapper<UserEntity, User> {
    override fun mapToEntity(type: User): UserEntity {
        return UserEntity(
            type.name,
            type.points,
            type.id
        )
    }

    override fun mapFromEntity(type: UserEntity): User {
        return User(
            type.name,
            type.points,
            type.id
        )
    }
}