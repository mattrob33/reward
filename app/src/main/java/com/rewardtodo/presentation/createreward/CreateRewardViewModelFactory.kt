package com.rewardtodo.presentation.createreward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.global.UserManager
import javax.inject.Inject

class CreateRewardViewModelFactory @Inject constructor (
    private val rewardRepository: RewardRepository,
    private val prefs: PreferencesHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateRewardViewModel(
            rewardRepository,
            prefs
        ) as T
    }

}