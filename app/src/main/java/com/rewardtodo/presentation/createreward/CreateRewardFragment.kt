package com.rewardtodo.presentation.createreward

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.rewardtodo.R
import com.rewardtodo.domain.Reward
import com.rewardtodo.global.RewardApplication
import com.rewardtodo.presentation.BaseFragment
import com.rewardtodo.presentation.mapper.RewardIconMapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_create_reward.*
import javax.inject.Inject

class CreateRewardFragment : BaseFragment() {

    private val args: CreateRewardFragmentArgs by navArgs()

    @Inject lateinit var viewModelFactory: CreateRewardViewModelFactory

    private val viewModel: CreateRewardViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory)[CreateRewardViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_reward, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id: String? = args.editRewardId
        if (id == null) {
            viewModel.mode = CreateRewardViewModel.Mode.CREATE
        }
        else {
            viewModel.mode = CreateRewardViewModel.Mode.EDIT
            viewModel.setEditRewardId(id)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        val iconAdapter = RewardIconAdapter(viewModel)
        iconAdapter.items = viewModel.iconsList
        reward_icon_list_view.adapter = iconAdapter
        reward_icon_list_view.layoutManager = GridLayoutManager(requireContext(), 5, GridLayoutManager.VERTICAL, false)

        reward_icon.setOnClickListener {
            reward_icon_list_view.visibility = View.VISIBLE
        }

        reward_points_seekbar.setLabelFormatter {
            if (it.toInt() in viewModel.pointVals.indices) {
                viewModel.pointVals[it.toInt()].toString()
            }
            else {
                "Custom"
            }
        }

        create_reward_button.setOnClickListener {
            val title = reward_title.text.toString()
            val desc = reward_description.text.toString()
            val points = viewModel.pointVals[reward_points_seekbar.value.toInt()]
            val icon = viewModel.icon.value?.peekContent() ?: Reward.Icon.STORE

            viewModel.submitReward(title, desc, points, icon)

            findNavController().navigateUp()
        }

        close_button_create_rewards.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.icon.observe(viewLifecycleOwner) {
            val icon = it.peekContent()
            val iconResId = RewardIconMapper.mapToView(icon)
            reward_icon.setImageResource(iconResId)
            reward_icon_list_view.visibility = View.GONE
        }

        viewModel.editReward.observe(viewLifecycleOwner) {
            if (viewModel.mode == CreateRewardViewModel.Mode.EDIT) {
                viewModel.onIconSelected(it.icon)

                reward_title.setText(it.title)
                reward_description.setText(it.description)

                val pos = viewModel.pointVals.indexOf(it.points)
                if (pos == -1)
                    reward_points_seekbar.value = reward_points_seekbar.maximumValue
                else
                    reward_points_seekbar.value = pos.toFloat()

                create_reward_button.text = RewardApplication.context.getString(R.string.save)
            }
        }
    }

}
