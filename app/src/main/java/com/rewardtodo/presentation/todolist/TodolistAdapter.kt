package com.rewardtodo.presentation.todolist

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rewardtodo.R
import com.rewardtodo.RewardApplication
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.User
import com.rewardtodo.presentation.mapper.TodoItemMapper
import com.rewardtodo.presentation.models.TodoItemView
import kotlinx.coroutines.*
import javax.inject.Inject

class TodolistAdapter @Inject constructor(
    private val userRepo: UserRepository,
    private val todosRepo: TodoRepository
): ListAdapter<TodoItemView, RecyclerView.ViewHolder>(TodoItemDiffCallback()) {

//    private var items: MutableList<TodoItemView> = mutableListOf()

    private lateinit var user: User

    init {
        GlobalScope.launch {
            user = userRepo.getUser()
        }
    }

    private var isMultiselectMode = false
    private var numMultiselectItems = 0

    private val textColorNormal = ContextCompat.getColor(RewardApplication.context, R.color.textColor)
    private val textColorCompleted = Color.GRAY

    private val pointsBackgroundNormal = R.drawable.points_background
    private val pointsTextColorNormal = ContextCompat.getColor(RewardApplication.context, R.color.textColor)

    private val pointsBackgroundCompleted = R.drawable.points_background_completed
    private val pointsTextColorCompleted = Color.WHITE

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val todoItem = getItem(position)
        (viewHolder as ViewHolder).bind(todoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var row: ConstraintLayout = view.findViewById(R.id.todo_item_row)
        private var checkbox: CheckBox = view.findViewById(R.id.check_todo_item)
        private var titleText: TextView = view.findViewById(R.id.text_title)
        private var noteText: TextView = view.findViewById(R.id.text_note)
        private var pointsText: TextView = view.findViewById(R.id.text_points)

        fun bind(todoItem: TodoItemView) {
            row.setOnLongClickListener {
                isMultiselectMode = true
                if (!row.isSelected)
                    numMultiselectItems++
                row.isSelected = true
                return@setOnLongClickListener true
            }

            row.setOnClickListener {
                if (isMultiselectMode) {
                    if (!row.isSelected) {
                        numMultiselectItems++
                        row.isSelected = true
                    }
                    else {
                        numMultiselectItems--
                        row.isSelected = false
                        if (numMultiselectItems == 0)
                            isMultiselectMode = false
                    }
                }
            }

            titleText.text = todoItem.title

            checkbox.setOnCheckedChangeListener(null)
            checkbox.isChecked = todoItem.done
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val item = TodoItemMapper.mapFromView(todoItem)

                item.done = isChecked
                updateStyleForState(isChecked)

                GlobalScope.launch {
                    delay(400) // allow time for the material checkbox animation to complete
                    todosRepo.updateTodoItem(item)
                }

                user.points = if (isChecked)
                    user.points + item.points
                else
                    user.points - item.points
                userRepo.updateUser(user)
            }

            noteText.text = todoItem.note
            noteText.visibility =
                if (todoItem.note.isEmpty())
                    View.GONE
                else
                    View.VISIBLE

            pointsText.text = "${todoItem.points}"

            updateStyleForState(todoItem.done)
        }

        private fun updateStyleForState(isChecked: Boolean) {
            val textColor = if (isChecked) textColorCompleted else textColorNormal

            val pointsBackground = if (isChecked) pointsBackgroundCompleted else pointsBackgroundNormal
            val pointsTextColor = if (isChecked) pointsTextColorCompleted else pointsTextColorNormal

            row.setOnLongClickListener {
                isMultiselectMode = true
                if (!row.isSelected)
                    numMultiselectItems++
                row.isSelected = true
                return@setOnLongClickListener true
            }

            row.setOnClickListener {
                if (isMultiselectMode) {
                    if (!row.isSelected) {
                        numMultiselectItems++
                        row.isSelected = true
                    }
                    else {
                        numMultiselectItems--
                        row.isSelected = false
                        if (numMultiselectItems == 0)
                            isMultiselectMode = false
                    }
                }
            }

            titleText.apply {
                paintFlags =
                    if (isChecked)
                        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    else
                        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                setTextColor(textColor)
            }

            noteText.setTextColor(textColor)

            pointsText.setBackgroundResource(pointsBackground)
            pointsText.setTextColor(pointsTextColor)
        }
    }

    private class TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItemView>() {
        override fun areItemsTheSame(oldItem: TodoItemView, newItem: TodoItemView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoItemView, newItem: TodoItemView): Boolean {
            return oldItem == newItem
        }
    }
}