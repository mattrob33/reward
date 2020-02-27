package com.rewardtodo.presentation.rewards

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rewardtodo.R
import com.rewardtodo.domain.Reward
import com.rewardtodo.global.RewardApplication
import com.rewardtodo.presentation.mapper.RewardIconMapper
import com.rewardtodo.presentation.mapper.RewardMapper
import com.rewardtodo.presentation.models.RewardView
import javax.inject.Inject

class RewardAdapter @Inject constructor(
    private val viewModel: RewardsViewModel,
    private val fragment: RewardsFragment
): RecyclerView.Adapter<RewardAdapter.ViewHolder>() {

    var items: List<RewardView> = mutableListOf()

    var position: Int = -1
        private set

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rewardView = items[position]

        holder.row.setOnClickListener {
            val reward = RewardMapper.mapFromView(rewardView)
            attemptRewardPurchase(reward)
        }

        holder.row.setOnLongClickListener {
            this.position = position
            return@setOnLongClickListener false
        }

        holder.image.setImageResource( RewardIconMapper.mapToView(rewardView.icon) )

        val color = ContextCompat.getColor(RewardApplication.context, R.color.rewardImageColor)
        if (Build.VERSION.SDK_INT >= 29)
            holder.image.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        else
            holder.image.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        holder.titleText.text = rewardView.title

        holder.descText.text = rewardView.description
        holder.descText.visibility =
            if (rewardView.description.isEmpty())
                View.GONE
            else
                View.VISIBLE

        holder.pointsText.text = "${rewardView.points}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.reward_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun attemptRewardPurchase(reward: Reward) {
        viewModel.requestPurchase(reward)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        var row: ConstraintLayout = view.findViewById(R.id.reward_item_row)
        var image: ImageView = view.findViewById(R.id.image_reward)
        var titleText: TextView = view.findViewById(R.id.text_title)
        var descText: TextView = view.findViewById(R.id.text_desc)
        var pointsText: TextView = view.findViewById(R.id.text_points)


        init {
            view.setOnCreateContextMenuListener(this)
        }


        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.setHeaderTitle("Select The Action")
            menu?.add(0, R.id.menu_reward_edit, 0, "Edit")
            menu?.add(0, R.id.menu_reward_delete, 0, "Delete")
        }
    }

}