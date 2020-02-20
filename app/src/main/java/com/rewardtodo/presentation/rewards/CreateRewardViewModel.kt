package com.rewardtodo.presentation.rewards

import androidx.lifecycle.ViewModel
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.global.UserManager
import javax.inject.Inject

class CreateRewardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val rewardRepository: RewardRepository
) : ViewModel() {

    fun createReward(reward: Reward) {
        rewardRepository.addReward(reward)
    }

}
