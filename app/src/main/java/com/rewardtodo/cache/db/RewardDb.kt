package com.rewardtodo.cache.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rewardtodo.global.RewardApplication
import com.rewardtodo.cache.model.RewardEntity
import com.rewardtodo.cache.model.TodoItemEntity
import com.rewardtodo.cache.model.UserEntity

@Database(entities = [UserEntity::class, TodoItemEntity::class, RewardEntity::class], version = 1, exportSchema = false)
abstract class RewardDb: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun todoItemDao(): TodoItemDao
    abstract fun rewardDao(): RewardDao

    companion object {
        const val USER_TABLE = "user"
        const val TODOS_TABLE = "todos"
        const val REWARDS_TABLE = "rewards"

        private const val DATABASE_NAME = "reward-db"

        @Volatile
        private var instance: RewardDb? = null

        fun getInstance(): RewardDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase().also { instance = it }
            }
        }

        private fun buildDatabase(): RewardDb {
            return Room.databaseBuilder(RewardApplication.context, RewardDb::class.java, DATABASE_NAME)
                .build()
        }
    }
}