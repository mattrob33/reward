package com.rewardtodo.di.component

import com.rewardtodo.presentation.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MainActivitySubComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}