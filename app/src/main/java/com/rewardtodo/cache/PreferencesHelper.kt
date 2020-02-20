package com.rewardtodo.cache

import android.content.Context
import android.content.SharedPreferences
import com.rewardtodo.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
open class PreferencesHelper @Inject constructor(context: Context) {
    companion object {
        private const val PREF_NAME = "com.rewardtodo"

        private const val PREF_CURRENT_USER_ID = "current_user_id"
        private const val PREF_SHOW_COMPLETED_ITEMS = "show_completed_items"
    }

    private val sharedPrefs: SharedPreferences

    init {
        sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var currentUserId: String
        get() = sharedPrefs.getString(PREF_CURRENT_USER_ID, "") ?: ""
        set(id) = sharedPrefs.edit().putString(PREF_CURRENT_USER_ID, id).apply()

    var showCompletedTodoItems: Boolean
        get() = sharedPrefs.getBoolean(PREF_SHOW_COMPLETED_ITEMS, false)
        set(show) = sharedPrefs.edit().putBoolean(PREF_SHOW_COMPLETED_ITEMS, show).apply()
}