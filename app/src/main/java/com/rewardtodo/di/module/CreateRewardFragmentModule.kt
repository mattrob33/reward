package com.rewardtodo.di.module

import com.rewardtodo.presentation.createreward.CreateRewardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CreateRewardFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeCreateRewardFragment(): CreateRewardFragment

}