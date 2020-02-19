package com.rewardtodo.cache.mapper

interface Mapper<E, D> {
    fun mapToEntity(type: D): E
    fun mapFromEntity(type: E): D
}