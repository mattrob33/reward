package com.rewardtodo.di.module

import com.rewardtodo.presentation.rewards.RewardsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RewardsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeRewardsFragment(): RewardsFragment

}