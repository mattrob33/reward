package com.rewardtodo.presentation.todolist

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.rewardtodo.R
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.domain.TodoItem
import com.rewardtodo.presentation.BaseFragment
import com.rewardtodo.presentation.MainActivity
import com.rewardtodo.util.hideKeyboard
import com.rewardtodo.util.showKeyboard
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_todolist.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import javax.inject.Inject


class TodolistFragment : BaseFragment() {

    private lateinit var itemsAdapter: TodolistAdapter
    @Inject lateinit var viewModelFactory: TodolistViewModelFactory

    @Inject lateinit var userRepo: UserRepository
    @Inject lateinit var todosRepo: TodoRepository

    private val viewModel: TodolistViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory)[TodolistViewModel::class.java]
    }

    private var actionMode: ActionMode? = null

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.todolist_action_mode_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            val iconColor = ContextCompat.getColor(requireContext(), R.color.iconTintColor)
            menu.findItem(R.id.menu_delete)!!.icon!!.setTint(iconColor)
            return true
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_delete -> {
                    viewModel.deleteSelectedItems()
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            viewModel.clearSelectedItems()
            actionMode = null
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_todolist, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()
        observeViewModel()
        start()
    }

    override fun onNavigateAway() {
        actionMode?.finish()
        actionMode = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todolist_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_hide_done -> {
                viewModel.toggleHideCompletedItems()
                true
            }
            else -> false
        }
    }

    /**
     * If touch was outside of the Add Item row's view bounds, clear focus from the EditText
     * and hide the keyboard.
     */
    override fun dispatchTouchEvent(ev: MotionEvent?) {
        ev?.let {
            val x = ev.rawX.toInt()
            val y = ev.rawY.toInt()

            val addItemRow = Rect()
            todolist_add_item_row.getGlobalVisibleRect(addItemRow)

            if (!addItemRow.contains(x, y)) {
                edit_text_add_item_title.clearFocus()
                hideKeyboard(requireContext(), edit_text_add_item_title)
            }
        }
    }

    private fun setupView() {
        (activity!! as AppCompatActivity).setSupportActionBar(todolist_toolbar)
        (activity!! as AppCompatActivity).supportActionBar?.title = ""

        itemsAdapter = TodolistAdapter(viewModel, this)
        todolist_view.layoutManager = LinearLayoutManager(requireContext())
        todolist_view.adapter = itemsAdapter

        button_add_item_points.setOnClickListener {
            viewModel.cycleNewItemPoints()
        }

        button_add_item.setOnClickListener {
            val itemText = edit_text_add_item_title.text.toString().trim()
            val itemPoints = button_add_item_points.text.toString().toInt()

            if (itemText.isNotEmpty()) {
                viewModel.addTodoItem(
                    TodoItem(
                        title = itemText,
                        points = itemPoints,
                        done = false
                    )
                )
                edit_text_add_item_title.setText("")
            }
        }

        fab_add_todo.setOnClickListener {
            todolist_add_item_row.visibility = View.VISIBLE
            fab_add_todo.visibility = View.GONE
            edit_text_add_item_title.requestFocus()
            showKeyboard(requireContext(), edit_text_add_item_title)
        }

        KeyboardVisibilityEvent.setEventListener(activity!!, viewLifecycleOwner, object : KeyboardVisibilityEventListener {
            override fun onVisibilityChanged(isOpen: Boolean) {
                activity?.let {
                    if (activity is MainActivity) {
                        if (isOpen) {
                            (activity as MainActivity).hideBottomNavBar()
                            todolist_add_item_row.visibility = View.VISIBLE
                            fab_add_todo.visibility = View.GONE
                        }
                        else {
                            (activity as MainActivity).showBottomNavBar()
                            todolist_add_item_row.visibility = View.GONE
                            fab_add_todo.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.items.observe(viewLifecycleOwner) {
            itemsAdapter.submitList(it)
            if (it.isEmpty()) {
                todolist_view.visibility = View.GONE
                todolist_empty_view.visibility = View.VISIBLE
            }
            else {
                todolist_view.visibility = View.VISIBLE
                todolist_empty_view.visibility = View.GONE
            }
        }

        viewModel.numItemsSelected.observe(viewLifecycleOwner) { numItemsSelected ->
            if (numItemsSelected > 0) {
                if (actionMode == null) {
                    actionMode = (activity as AppCompatActivity).startSupportActionMode(actionModeCallback)
                }
            }
            else {
                actionMode?.finish()
            }


        }

        viewModel.newItemPoints.observe(viewLifecycleOwner) {
            button_add_item_points.text = it.toString()
        }
    }

    private fun start() {
        viewModel.getTodoItems()
    }
}