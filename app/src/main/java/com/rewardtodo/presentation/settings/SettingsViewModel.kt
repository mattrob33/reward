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
import com.rewardtodo.util.Event
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

    enum class SettingsEvent {
        USER_ADDED,
        USER_SWITCHED,
        USER_RENAME_REQUESTED,
        USER_DELETED
    }

    enum class SettingsMode {
        DEFAULT,
        MANAGE_PROFILES
    }

    private val _event = MutableLiveData<Event<SettingsEvent>>()
    val eventBus = _event

    private val _settingsMode = MutableLiveData<SettingsMode>()
    val settingsMode = _settingsMode

    private val _userList = MutableLiveData<List<UserView>>()
    val userList = _userList

    var editingUserId = ""

    init {
        _settingsMode.value = SettingsMode.DEFAULT
        refreshUserList()
    }

    private fun refreshUserList() {
        viewModelScope.launch {
            userManager.userListFlow.collect { userList ->
                val adjustedUserList = mutableListOf<UserView>()
                adjustedUserList.addAll(userList.map { UserMapper.mapToView(it) })

                if (_settingsMode.value != SettingsMode.MANAGE_PROFILES) {
                    val addUserOption = UserView(
                        name = "Add Profile",
                        id = ADD_USER_OPTION
                    )
                    adjustedUserList.add(addUserOption)
                }
                _userList.value = adjustedUserList
            }
        }
    }

    fun onUserItemClicked(userId: String) {
        when (settingsMode.value) {
            SettingsMode.DEFAULT -> {
                when (userId) {
                    ADD_USER_OPTION -> {
                        userRepo.addUser(User(name = "New User"))
                        _event.value = Event(SettingsEvent.USER_ADDED)
                    }
                    else -> {
                        userManager.changeCurrentUser(userId)
                        _event.value = Event(SettingsEvent.USER_SWITCHED)
                    }
                }
            }

            SettingsMode.MANAGE_PROFILES -> {
                editingUserId = userId
                _event.value = Event(SettingsEvent.USER_RENAME_REQUESTED)
            }
        }
    }

    fun onUserItemLongPressed(userId: String) {
        when (userId) {
            ADD_USER_OPTION -> {}
            else -> userRepo.deleteUser(userId)
        }
    }

    fun toggleManageProfiles() {
        _settingsMode.value = when(_settingsMode.value) {
            SettingsMode.MANAGE_PROFILES -> SettingsMode.DEFAULT
            else -> {
                SettingsMode.MANAGE_PROFILES
            }
        }
        refreshUserList()
    }

    fun cancelRenameUser() {
        editingUserId = ""
        _settingsMode.value = SettingsMode.DEFAULT
    }

    fun renameUser(name: String) {
        viewModelScope.launch {
            val user = userRepo.getUser(editingUserId)
            user.name = name
            userRepo.updateUser(user)
            _settingsMode.value = SettingsMode.DEFAULT
        }
    }
}