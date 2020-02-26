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

    suspend fun deleteUser(id: String) = userDao.delete(id)

    private suspend fun hasAddedUser(): Boolean {
        return userDao.numUsers() > 0
    }

    suspend fun getUser(id: String): User {
        if (hasAddedUser()) {
            val existingUser = userDao.getUser(id)
            existingUser?.let {
                return UserMapper.mapFromEntity(existingUser)
            }
        }
        val newUser = User(name = "My Name")
        userDao.insert( UserMapper.mapToEntity(newUser) )
        return newUser
    }

    fun getUserFlow(id: String): Flow<User> = userDao.getUserFlow(id).map { userEntity ->
        if (userEntity != null)
            UserMapper.mapFromEntity(userEntity)
        else
            User()
    }

    fun getUserListFlow(): Flow<List<User>> = userDao.getUserListFlow().map { entityList ->
        entityList.map { userEntity ->
            if (userEntity != null)
                UserMapper.mapFromEntity(userEntity)
            else
                User()
        }
    }

}