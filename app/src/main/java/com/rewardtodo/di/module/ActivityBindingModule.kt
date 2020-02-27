package com.rewardtodo.di.module

import com.rewardtodo.di.scopes.FragmentScope
import com.rewardtodo.presentation.createreward.CreateRewardFragment
import com.rewardtodo.presentation.rewards.RewardsFragment
import com.rewardtodo.presentation.settings.SettingsFragment
import com.rewardtodo.presentation.todolist.TodolistFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [TodolistFragmentModule::class])
    abstract fun bindTodolistFragmentFragment(): TodolistFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [RewardsFragmentModule::class])
    abstract fun bindRewardsFragment(): RewardsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CreateRewardFragmentModule::class])
    abstract fun bindCreateRewardFragment(): CreateRewardFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SettingsFragmentModule::class])
    abstract fun bindSettingsFragment(): SettingsFragment

}