package com.rewardtodo.presentation.rewards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rewardtodo.R
import com.rewardtodo.domain.Reward
import com.rewardtodo.presentation.mapper.RewardIconMapper
import javax.inject.Inject

class RewardIconAdapter @Inject constructor(
    private val viewModel: CreateRewardViewModel
): RecyclerView.Adapter<RewardIconAdapter.ViewHolder>() {

    var items: List<Reward.Icon> = mutableListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val icon = items[position]

        holder.cell.setOnClickListener {
            viewModel.onIconSelected(icon)
        }

        holder.image.setImageResource( RewardIconMapper.mapToView(icon) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.reward_icon_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cell: ConstraintLayout = view.findViewById(R.id.reward_icon_item_cell)
        var image: ImageView = view.findViewById(R.id.reward_icon_item_image)
    }

}