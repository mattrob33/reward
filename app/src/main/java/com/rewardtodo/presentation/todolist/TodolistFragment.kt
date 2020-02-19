package com.rewardtodo.presentation.todolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return inflater.inflate(R.layout.fragment_todolist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()
        observeViewModel()
        start()
    }

    private fun setupView() {
        (activity!! as AppCompatActivity).setSupportActionBar(todolist_toolbar)

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
        }
    }

    private fun start() {
        viewModel.getTodoItems()
    }
}