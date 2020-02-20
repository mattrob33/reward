package com.rewardtodo.di.module

import com.rewardtodo.presentation.rewards.CreateRewardFragment
import com.rewardtodo.presentation.rewards.RewardsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CreateRewardFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeCreateRewardFragment(): CreateRewardFragment

}