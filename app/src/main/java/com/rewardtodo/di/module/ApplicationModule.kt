package com.rewardtodo.di.module

import android.app.Application
import android.content.Context
import com.rewardtodo.cache.db.RewardDao
import com.rewardtodo.cache.db.RewardDb
import com.rewardtodo.cache.db.TodoItemDao
import com.rewardtodo.cache.db.UserDao
import com.rewardtodo.cache.repo.RewardCacheRepository
import com.rewardtodo.cache.repo.TodoCacheRepository
import com.rewardtodo.cache.repo.UserCacheRepository
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    @ApplicationScope
    fun provideContext(application: Application): Context = application

    @Provides
    @ApplicationScope
    fun provideTodoRepository(cacheRepo: TodoCacheRepository) = TodoRepository(cacheRepo)

    @Provides
    @ApplicationScope
    fun provideTodoCacheRepository(todoItemDao: TodoItemDao) = TodoCacheRepository(todoItemDao)

    @Provides
    @ApplicationScope
    fun provideTodoItemDao() = RewardDb.getInstance().todoItemDao()

    @Provides
    @ApplicationScope
    fun provideRewardRepository(cacheRepo: RewardCacheRepository) = RewardRepository(cacheRepo)

    @Provides
    @ApplicationScope
    fun provideRewardCacheRepository(rewardDao: RewardDao) = RewardCacheRepository(rewardDao)

    @Provides
    @ApplicationScope
    fun provideRewardDao() = RewardDb.getInstance().rewardDao()

    @Provides
    @ApplicationScope
    fun provideUserRepository(cacheRepo: UserCacheRepository) = UserRepository(cacheRepo)

    @Provides
    @ApplicationScope
    fun provideUserCacheRepository(userDao: UserDao) = UserCacheRepository(userDao)

    @Provides
    @ApplicationScope
    fun provideUserDao() = RewardDb.getInstance().userDao()
}