package com.rewardtodo.di.module

import com.rewardtodo.presentation.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

}