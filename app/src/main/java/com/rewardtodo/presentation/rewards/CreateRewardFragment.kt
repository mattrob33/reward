package com.rewardtodo.presentation.rewards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rewardtodo.R
import com.rewardtodo.domain.Reward
import com.rewardtodo.presentation.BaseFragment
import com.rewardtodo.presentation.mapper.RewardIconMapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_create_reward.*
import javax.inject.Inject

class CreateRewardFragment : BaseFragment() {

    companion object {
        fun newInstance() = CreateRewardFragment()
    }

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

            viewModel.createReward(title, desc, points, icon)

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
    }

}
