package com.rewardtodo.presentation.settings

import android.graphics.*
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rewardtodo.R
import com.rewardtodo.global.RewardApplication
import com.rewardtodo.presentation.models.UserView
import com.rewardtodo.presentation.settings.SettingsViewModel.SettingsMode
import javax.inject.Inject

class UserListAdapter @Inject constructor(
    private val viewModel: SettingsViewModel
): ListAdapter<UserView, UserListAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userView = getItem(position)

        holder.cell.setOnClickListener {
            viewModel.onUserItemClicked(userView.id)
        }

        holder.cell.setOnLongClickListener {
            viewModel.onUserItemLongPressed(userView.id)
            return@setOnLongClickListener true
        }

        val color: Int

        // TODO : this logic should be at model & viewmodel layer
        if (userView.id == SettingsViewModel.ADD_USER_OPTION) {
            color = Color.GRAY
            holder.image.setImageResource(R.drawable.ic_add)
        }
        else {
            if (viewModel.settingsMode.value == SettingsMode.MANAGE_PROFILES) {
                color = Color.GRAY
                holder.image.setImageResource(R.drawable.ic_pencil)
            }
            else {
                color = ContextCompat.getColor(RewardApplication.context, R.color.textColor)
                holder.image.setImageResource(R.drawable.ic_person)
            }
        }

        holder.name.setTextColor(color)

        if (Build.VERSION.SDK_INT >= 29) {
            holder.image.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
            holder.image.background.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        }
        else {
            holder.image.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            holder.image.background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }

        holder.name.text = userView.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cell: ConstraintLayout = view.findViewById(R.id.user_item_cell)
        var image: ImageView = view.findViewById(R.id.user_item_icon)
        var name: TextView = view.findViewById(R.id.user_item_name)
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<UserView>() {
        override fun areItemsTheSame(oldUser: UserView, newUser: UserView): Boolean {
            return oldUser.id == newUser.id
        }

        override fun areContentsTheSame(oldUser: UserView, newUser: UserView): Boolean {
            return oldUser == newUser
        }
    }
}