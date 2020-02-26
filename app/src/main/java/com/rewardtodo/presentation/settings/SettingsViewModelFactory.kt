package com.rewardtodo.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.global.UserManager
import javax.inject.Inject

class SettingsViewModelFactory @Inject constructor (
    private val userManager: UserManager,
    private val userRepository: UserRepository,
    private val prefs: PreferencesHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(userManager, userRepository, prefs) as T
    }

}