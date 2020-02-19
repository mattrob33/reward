package com.rewardtodo.presentation.rewards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rewardtodo.R
import com.rewardtodo.domain.Reward
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_rewards.*
import javax.inject.Inject

class RewardsFragment : Fragment() {

    @Inject lateinit var itemsAdapter: RewardAdapter
    @Inject lateinit var viewModelFactory: RewardsViewModelFactory

    private val viewModel: RewardsViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory)[RewardsViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rewards, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()
        observeViewModel()
        start()
    }

    private fun setupView() {
        (activity!! as AppCompatActivity).setSupportActionBar(rewards_toolbar)

        reward_list_view.layoutManager = LinearLayoutManager(requireContext())
        reward_list_view.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        reward_list_view.adapter = itemsAdapter

        fab_create_reward.setOnClickListener {
            viewModel.createReward(
                Reward(
                    title = "A Major Reward",
                    description = "It's a major reward.",
                    imagePath = "",
                    points = 16
                )
            )
        }
    }

    private fun observeViewModel() {
        viewModel.pointsText.observe(viewLifecycleOwner, Observer {
            text_points.text = it
        })

        viewModel.items.observe(viewLifecycleOwner) {
            itemsAdapter.items = it
            itemsAdapter.notifyDataSetChanged()
        }
    }

    private fun start() {
        viewModel.getRewards()
    }
}