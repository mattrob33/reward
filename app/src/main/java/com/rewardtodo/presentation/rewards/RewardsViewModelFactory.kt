package com.rewardtodo.presentation.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import javax.inject.Inject

class RewardsViewModelFactory @Inject constructor (
    private val userRepository: UserRepository,
    private val rewardRepository: RewardRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RewardsViewModel(userRepository, rewardRepository) as T
    }

}