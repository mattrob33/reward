package com.rewardtodo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.verify
import com.rewardtodo.cache.PreferencesHelper
import com.rewardtodo.data.repo.TodoRepository
import com.rewardtodo.data.repo.UserRepository
import com.rewardtodo.presentation.factory.TodoFactory
import com.rewardtodo.presentation.todolist.TodolistViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class TodolistViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var mockTodoRepo: TodoRepository
    @Mock private lateinit var mockUserRepo: UserRepository

    private lateinit var fakePrefs: PreferencesHelper

    private lateinit var testViewModel: TodolistViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        fakePrefs = PreferencesHelper(ApplicationProvider.getApplicationContext())

        testViewModel = TodolistViewModel(mockUserRepo, mockTodoRepo, fakePrefs)
    }

    @Test
    fun whenToggleDonePrefsToggles() = runBlocking<Unit> {
        val startVal = fakePrefs.showCompletedTodoItems
        testViewModel.toggleHideCompletedItems()
        assert(startVal != fakePrefs.showCompletedTodoItems)
    }

    @Test
    fun whenAddTodoItemThenTodoItemAddedToRepository() = runBlocking<Unit> {
        val todo = TodoFactory.createTodoItem()
        testViewModel.addTodoItem(todo)
        verify(mockTodoRepo).addTodoItem(todo)
    }

    @Test
    fun whenGetTodoItemThenRepositoryCalled() {
        runBlocking {
            testViewModel.getTodoItems()
        }
        verify(mockTodoRepo).getAllTodoItems()
    }
}
