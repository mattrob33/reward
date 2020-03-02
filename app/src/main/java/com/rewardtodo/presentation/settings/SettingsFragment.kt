package com.rewardtodo.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rewardtodo.R
import com.rewardtodo.global.UserManager
import com.rewardtodo.presentation.BaseFragment
import com.rewardtodo.presentation.settings.SettingsViewModel.SettingsEvent
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject lateinit var userManager: UserManager
    @Inject lateinit var viewModelFactory: SettingsViewModelFactory

    private lateinit var usersAdapter: UserListAdapter

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory)[SettingsViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        usersAdapter = UserListAdapter(viewModel)
        settings_user_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        settings_user_list.adapter = usersAdapter

        button_manage_profiles.setOnClickListener {
            viewModel.toggleManageProfiles()
        }

        viewModel.userList.observe(viewLifecycleOwner) {
            usersAdapter.submitList(it)
        }

        viewModel.settingsMode.observe(viewLifecycleOwner) {
            usersAdapter.notifyDataSetChanged()
        }

        viewModel.eventBus.observe(viewLifecycleOwner) {
            when (it.peekContent()) {
                SettingsEvent.USER_RENAME_REQUESTED -> {
                    val event = it.getContentIfNotHandled()
                    event?.let {
                        val etUsername = EditText(requireContext()).apply {
                            hint = "User's name"
                        }

                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Rename User")
                            .setView(etUsername)
                            .setPositiveButton("Ok") { _, _ ->
                                viewModel.renameUser(etUsername.text.toString())
                            }
                            .setNeutralButton("Cancel") { _, _ ->
                                viewModel.cancelRenameUser()
                            }
                            .show()
                    }
                }
                else -> {}
            }
        }
    }
}