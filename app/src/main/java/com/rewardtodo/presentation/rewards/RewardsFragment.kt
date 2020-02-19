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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.rewardtodo.R
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.domain.User
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_rewards.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RewardsFragment : Fragment() {

    lateinit var itemsAdapter: RewardAdapter
    @Inject lateinit var viewModelFactory: RewardsViewModelFactory
    @Inject lateinit var userRepo: UserRepository

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

        itemsAdapter = RewardAdapter(viewModel, userRepo)

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

            if (it.isEmpty()) {
                reward_list_view.visibility = View.GONE
                reward_empty_view.visibility = View.VISIBLE
            }
            else {
                reward_list_view.visibility = View.VISIBLE
                reward_empty_view.visibility = View.GONE
            }
        }

        viewModel.onRequestPurchase.observe(viewLifecycleOwner, Observer { rewardEvent ->
            rewardEvent.getContentIfNotHandled()?.let { reward ->
                if (reward.points > viewModel.user.points) {
                    Snackbar.make(rewards_root, "You don't have enough points for this reward yet", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(reward.title)
                        .setMessage("Purchase ${reward.title} for ${reward.points} points?")
                        .setPositiveButton("Purchase") { _, _ ->
                            viewModel.purchaseReward(reward)
                            showRewardPurchasedMessage(reward)
                        }
                        .setNegativeButton("No", null)
                        .show()
                }
            }
        })
    }

    private fun start() {
        viewModel.getRewards()
    }

    private fun showRewardPurchasedMessage(reward: Reward) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hooray!")
            .setMessage("You earned ${reward.title}! Way to go, you deserve it.")
            .setPositiveButton("Thanks!", null)
            .show()
    }
}