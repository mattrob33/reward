package com.rewardtodo.presentation.rewards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.rewardtodo.R
import com.rewardtodo.data.repo.RewardRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.Reward
import com.rewardtodo.presentation.BaseFragment
import com.rewardtodo.presentation.mapper.RewardMapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_rewards.*
import javax.inject.Inject

class RewardsFragment : BaseFragment() {

    private lateinit var itemsAdapter: RewardAdapter
    @Inject lateinit var viewModelFactory: RewardsViewModelFactory

    @Inject lateinit var rewardRepo: RewardRepository
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(reward_list_view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        start()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val rewardItemView = with (itemsAdapter) {
            items[position]
        }

        val rewardItem = RewardMapper.mapFromView(rewardItemView)

        return when (item.itemId) {
            R.id.menu_reward_edit -> {
                val action = RewardsFragmentDirections.actionCreateReward()
                action.editRewardId = rewardItem.id
                findNavController().navigate(action)
                true
            }
            R.id.menu_reward_delete -> {
                rewardRepo.deleteReward(rewardItem)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun setupView() {
        (activity!! as AppCompatActivity).setSupportActionBar(rewards_toolbar)

        itemsAdapter = RewardAdapter(viewModel, this)

        reward_list_view.layoutManager = LinearLayoutManager(requireContext())
        reward_list_view.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        reward_list_view.adapter = itemsAdapter

        fab_create_reward.setOnClickListener {
            findNavController().navigate(R.id.action_create_reward)
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
        viewModel.start()
    }

    private fun showRewardPurchasedMessage(reward: Reward) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hooray!")
            .setMessage("You earned ${reward.title}! Way to go, you deserve it.")
            .setPositiveButton("Thanks!", null)
            .show()
    }
}