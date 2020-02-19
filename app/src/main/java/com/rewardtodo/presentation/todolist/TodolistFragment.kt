package com.rewardtodo.presentation.todolist

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.rewardtodo.R
import com.rewardtodo.domain.TodoItem
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_todolist.*
import javax.inject.Inject


class TodolistFragment : Fragment() {

    @Inject lateinit var itemsAdapter: TodolistAdapter
    @Inject lateinit var viewModelFactory: TodolistViewModelFactory

    private val viewModel: TodolistViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory)[TodolistViewModel::class.java]
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todolist_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_hide_done).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_hide_done -> viewModel.toggleHideDone()
        }
        return true
    }

    private fun setupView() {
        (activity!! as AppCompatActivity).setSupportActionBar(todolist_toolbar)
        (activity!! as AppCompatActivity).supportActionBar?.title = ""

        todolist_view.layoutManager = LinearLayoutManager(requireContext())
        todolist_view.adapter = itemsAdapter

        fab_add_todo.setOnClickListener {
            viewModel.addTodoItem(
                TodoItem(
                    title = "New item",
                    note = "",
                    points = 2,
                    done = false
                )
            )
        }
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
    }

    private fun start() {
        viewModel.getTodoItems()
    }
}