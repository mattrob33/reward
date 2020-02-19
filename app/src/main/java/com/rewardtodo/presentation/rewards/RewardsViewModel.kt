package com.rewardtodo.presentation.rewards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.presentation.mapper.RewardMapper
import com.rewardtodo.presentation.models.RewardView
import com.rewardtodo.util.cancelIfActive
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RewardsViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val rewardRepo: RewardRepository
) : ViewModel() {

    private var getTodoItemsJob: Job? = null

    private val _items = MutableLiveData<List<RewardView>>()
    val items = _items

    private val _pointsText = MutableLiveData<String>().apply {
        value = "Rewards"
    }
    val pointsText: LiveData<String> = _pointsText

    private val _points = MutableLiveData<Int>().apply {
        value = 0
    }
    val points: LiveData<Int> = _points

    init {
        viewModelScope.launch {
            userRepo.getUserFlow().collect { user ->
                _points.value = user.points
                _pointsText.value = "${user.points}"
            }
        }
    }

    fun createReward(reward: Reward) {
        rewardRepo.addReward(reward)
    }

    fun getRewards() {
        getTodoItemsJob?.cancelIfActive()

        getTodoItemsJob = viewModelScope.launch {
            rewardRepo.getAllRewards().collect { itemList ->
                _items.value = itemList.map { RewardMapper.mapToView(it) }
            }
        }
    }
}