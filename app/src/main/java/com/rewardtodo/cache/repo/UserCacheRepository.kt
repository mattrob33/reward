package com.rewardtodo.cache.repo

import com.rewardtodo.cache.db.UserDao
import com.rewardtodo.cache.mapper.TodoItemMapper
import com.rewardtodo.cache.mapper.UserMapper
import com.rewardtodo.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserCacheRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun addUser(user: User) = userDao.insert( UserMapper.mapToEntity(user))

    suspend fun updateUser(user: User) = userDao.update( UserMapper.mapToEntity(user) )

    suspend fun deleteUser(user: User) = userDao.delete( UserMapper.mapToEntity(user) )

    private suspend fun hasAddedUser(): Boolean {
        return userDao.numUsers() > 0
    }

    suspend fun getUser(): User {
        if (hasAddedUser()) {
            return UserMapper.mapFromEntity( userDao.getUser() )
        }
        val newUser = User()
        userDao.insert( UserMapper.mapToEntity(newUser) )
        return newUser
    }

    fun getUserFlow(): Flow<User> = userDao.getUserFlow().map { userEntity ->
        UserMapper.mapFromEntity(userEntity)
    }

}