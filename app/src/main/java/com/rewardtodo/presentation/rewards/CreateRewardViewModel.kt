package com.rewardtodo.presentation.rewards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.global.UserManager
import com.rewardtodo.util.Event
import javax.inject.Inject

class CreateRewardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val rewardRepository: RewardRepository,
    private val prefs: PreferencesHelper
) : ViewModel() {
    val pointVals = listOf(1, 2, 4, 8, 16)

    val iconsList = Reward.Icon.values().asList()

    private val _icon = MutableLiveData<Event<Reward.Icon>>()
    val icon: LiveData<Event<Reward.Icon>>
        get() = _icon

    init {
        _icon.value = Event(Reward.Icon.STORE)
    }

    fun onIconSelected(selectedIcon: Reward.Icon) {
        _icon.value = Event(selectedIcon)
    }

    fun createReward(title: String, desc: String, points: Int, icon: Reward.Icon) {
        val reward = Reward(
            title = title,
            description = desc,
            points = points,
            icon = icon,
            userId = prefs.currentUserId,
            imagePath = ""
        )
        rewardRepository.addReward(reward)
    }

}
