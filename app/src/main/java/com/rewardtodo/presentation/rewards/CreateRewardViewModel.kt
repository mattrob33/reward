package com.rewardtodo.presentation.rewards

import androidx.lifecycle.ViewModel
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.global.UserManager
import javax.inject.Inject

class CreateRewardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val rewardRepository: RewardRepository,
    private val prefs: PreferencesHelper
) : ViewModel() {

    fun createReward(title: String, desc: String, points: Int) {
        val reward = Reward(
            title = title,
            description = desc,
            points = points,
            userId = prefs.currentUserId,
            imagePath = ""
        )
        rewardRepository.addReward(reward)
    }

}
