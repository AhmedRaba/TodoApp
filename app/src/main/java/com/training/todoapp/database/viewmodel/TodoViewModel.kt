package com.training.todoapp.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.training.todoapp.database.AppDatabase
import com.training.todoapp.database.entity.Todo
import com.training.todoapp.database.repos.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository
    val readAllData: LiveData<List<Todo>>


    init {
        val todoDao = AppDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        readAllData = repository.readAllData
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

}