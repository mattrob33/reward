package com.rewardtodo.presentation.rewards

import androidx.lifecycle.*
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.domain.User
import com.rewardtodo.presentation.mapper.RewardMapper
import com.rewardtodo.presentation.models.RewardView
import com.rewardtodo.util.Event
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

    private val _requestPurchase = MutableLiveData<Event<Reward>>()

    val onRequestPurchase: LiveData<Event<Reward>>
        get() = _requestPurchase

    private val _items = MutableLiveData<List<RewardView>>()
    val items = _items

    private val _pointsText = MutableLiveData<String>().apply {
        value = "Rewards"
    }
    val pointsText: LiveData<String> = _pointsText

    lateinit var user: User

    private val _points = MutableLiveData<Int>().apply {
        value = 0
    }
    val points: LiveData<Int> = _points

    init {
        viewModelScope.launch {
            userRepo.getUserFlow().collect {
                user = it
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
                _items.value = itemList.map { RewardMapper.mapToView(it) }.sortedBy { it.numPurchases }
            }
        }
    }

    fun purchaseReward(reward: Reward) {
        reward.numPurchases++
        rewardRepo.updateReward(reward)

        user.points -= reward.points
        userRepo.updateUser(user)
    }

    fun requestPurchase(reward: Reward) {
        _requestPurchase.value = Event(reward)
    }
}