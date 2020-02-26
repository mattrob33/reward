package com.rewardtodo.presentation.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.User
import com.rewardtodo.global.UserManager
import com.rewardtodo.presentation.mapper.UserMapper
import com.rewardtodo.presentation.models.UserView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val userManager: UserManager,
    private val userRepo: UserRepository,
    private val prefs: PreferencesHelper
) : ViewModel() {

    companion object {
        const val ADD_USER_OPTION = "add_user_option_uuid"
    }

    private val _userList = MutableLiveData<List<UserView>>()
    val userList = _userList

    init {
        val addUserOption = UserView(
            name = "Add Profile",
            id = ADD_USER_OPTION
        )

        viewModelScope.launch {
            userManager.userListFlow.collect { userList ->
                val userListWithAddOption = mutableListOf<UserView>()
                userListWithAddOption.addAll(userList.map { UserMapper.mapToView(it) })
                userListWithAddOption.add(addUserOption)
                _userList.value = userListWithAddOption
            }
        }
    }

    fun onUserItemClicked(newUserId: String) {
        when (newUserId) {
            ADD_USER_OPTION -> userRepo.addUser(User(name = "New User"))
            else -> userManager.changeCurrentUser(newUserId)
        }
    }

    fun onUserItemLongPressed(userId: String) {
        when (userId) {
            ADD_USER_OPTION -> {}
            else -> {
                if (userId != prefs.currentUserId)
                    userRepo.deleteUser(userId)
            }
        }
    }
}