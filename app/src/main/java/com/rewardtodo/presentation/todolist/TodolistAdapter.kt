package com.rewardtodo.presentation.todolist

import android.graphics.Color
import android.graphics.Paint
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rewardtodo.R
import com.rewardtodo.global.RewardApplication
import com.rewardtodo.presentation.mapper.TodoItemMapper
import com.rewardtodo.presentation.models.TodoItemView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodolistAdapter @Inject constructor(
    private val todolistViewModel: TodolistViewModel,
    todolistFragment: TodolistFragment
): ListAdapter<TodoItemView, RecyclerView.ViewHolder>(TodoItemDiffCallback()) {

    private val textColorNormal = ContextCompat.getColor(RewardApplication.context, R.color.textColor)
    private val textColorCompleted = Color.GRAY

    private val pointsBackgroundNormal = R.drawable.points_background
    private val pointsTextColorNormal = ContextCompat.getColor(RewardApplication.context, R.color.pointsTextColor)

    private val pointsBackgroundCompleted = R.drawable.points_background_completed
    private val pointsTextColorCompleted = ContextCompat.getColor(RewardApplication.context, R.color.pointsTextColorCompleted)

    private var numSelectedItems = 0

    init {
        todolistViewModel.numItemsSelected.observe(todolistFragment.viewLifecycleOwner) {
            numSelectedItems = it
            notifyDataSetChanged()
        }
    }

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
                if (!row.isSelected) {
                    row.isSelected = true
                    todolistViewModel.addItemToSelectedList(TodoItemMapper.mapFromView(todoItem))
                }

                return@setOnLongClickListener true
            }

            row.setOnClickListener {
                if (numSelectedItems > 0) {
                    if (!row.isSelected) {
                        row.isSelected = true
                        todolistViewModel.addItemToSelectedList(TodoItemMapper.mapFromView(todoItem))
                    }
                    else {
                        row.isSelected = false
                        todolistViewModel.removeItemFromSelectedList(TodoItemMapper.mapFromView(todoItem))
                    }
                }
            }

            titleText.text = todoItem.title

            checkbox.setOnCheckedChangeListener(null)
            checkbox.isChecked = todoItem.done
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val item = TodoItemMapper.mapFromView(todoItem)
                todoItem.done = isChecked
                updateStyleForState(todoItem)

                GlobalScope.launch {
                    delay(400) // allow time for the material checkbox animation to complete
                    todolistViewModel.toggleItem(item)
                }
            }

            noteText.text = todoItem.note
            noteText.visibility =
                if (todoItem.note.isEmpty())
                    View.GONE
                else
                    View.VISIBLE

            pointsText.text = "${todoItem.points}"

            updateStyleForState(todoItem)
        }

        private fun updateStyleForState(item: TodoItemView) {
            val textColor = if (item.done) textColorCompleted else textColorNormal

            val pointsBackground = if (item.done) pointsBackgroundCompleted else pointsBackgroundNormal
            val pointsTextColor = if (item.done) pointsTextColorCompleted else pointsTextColorNormal

            row.isSelected = todolistViewModel.selectedItems.contains(item.id)

            titleText.apply {
                paintFlags =
                    if (item.done)
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