package com.rewardtodo.presentation.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.global.UserManager
import javax.inject.Inject

class CreateRewardViewModelFactory @Inject constructor (
    private val userManager: UserManager,
    private val rewardRepository: RewardRepository,
    private val prefs: PreferencesHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateRewardViewModel(userManager, rewardRepository, prefs) as T
    }

}