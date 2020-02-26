package com.rewardtodo.presentation.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rewardtodo.R
import com.rewardtodo.presentation.models.UserView
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

        // TODO : this logic should be at model & viewmodel layer
        if (userView.id == SettingsViewModel.ADD_USER_OPTION)
            holder.image.setImageResource(R.drawable.ic_add)
        else
            holder.image.setImageResource(R.drawable.ic_person)

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