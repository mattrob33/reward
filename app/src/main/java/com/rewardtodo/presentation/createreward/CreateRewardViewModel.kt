package com.rewardtodo.presentation.createreward

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.util.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateRewardViewModel @Inject constructor(
    private val rewardRepository: RewardRepository,
    private val prefs: PreferencesHelper
) : ViewModel() {

    enum class Mode {
        CREATE,
        EDIT
    }

    var mode = Mode.CREATE
        set(m) {
            field = m
            if (m == Mode.CREATE) {
                _icon.value = Event(Reward.Icon.STORE)
            }
        }

    private var editId: String? = null

    private var _editRewardSnapshot: Reward? = null
    private val _editReward = MutableLiveData<Reward>()
    val editReward: LiveData<Reward>
        get() = _editReward

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

    fun submitReward(rewardTitle: String, rewardDesc: String, rewardPoints: Int, rewardIcon: Reward.Icon) {
        when (mode) {
            Mode.CREATE -> {
                val reward = Reward(
                    title = rewardTitle,
                    description = rewardDesc,
                    points = rewardPoints,
                    icon = rewardIcon,
                    userId = prefs.currentUserId,
                    imagePath = ""
                )
                rewardRepository.addReward(reward)
            }

            Mode.EDIT -> {
                val reward = requireNotNull(_editRewardSnapshot)
                reward.apply {
                    title = rewardTitle
                    description = rewardDesc
                    points = rewardPoints
                    icon = rewardIcon
                }
                rewardRepository.updateReward(reward)
            }
        }
    }

    fun setEditRewardId(id: String) {
        editId = id
        viewModelScope.launch {
            rewardRepository.getReward(id).collect {
                _editReward.value = it
                _editRewardSnapshot = it
            }
        }
    }

}
