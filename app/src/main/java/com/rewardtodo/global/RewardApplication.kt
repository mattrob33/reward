package com.rewardtodo.global

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class RewardApplication: Application(), HasAndroidInjector {
    companion object {
        lateinit var context: Context
    }

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject lateinit var prefs: PreferencesHelper

    @Inject lateinit var userManager: UserManager


    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        context = this

        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}