package com.rewardtodo.presentation.rewards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rewardtodo.R
import com.rewardtodo.domain.Reward
import com.rewardtodo.presentation.BaseFragment
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

    private val pointVals = listOf(1, 2, 4, 8, 16)

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
    }

    private fun setupView() {
        reward_points_seekbar.setLabelFormatter {
            if (it.toInt() in pointVals.indices) {
                pointVals[it.toInt()].toString()
            }
            else {
                "Custom"
            }
        }

        create_reward_button.setOnClickListener {
            val title = reward_title.text.toString()
            val desc = reward_description.text.toString()
            val points = pointVals[reward_points_seekbar.value.toInt()]

            viewModel.createReward(title, desc, points)

            findNavController().navigateUp()
        }

        close_button_create_rewards.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}
