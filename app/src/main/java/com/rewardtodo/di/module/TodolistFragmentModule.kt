package com.rewardtodo.di.module

import com.rewardtodo.presentation.todolist.TodolistFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TodolistFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeTodolistFragment(): TodolistFragment

}