package com.rewardtodo.di

import android.app.Application
import com.rewardtodo.global.RewardApplication
import com.rewardtodo.di.module.ActivityBindingModule
import com.rewardtodo.di.module.ApplicationModule
import com.rewardtodo.di.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [
    ActivityBindingModule::class,
    ApplicationModule::class,
    AndroidSupportInjectionModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: RewardApplication)

}