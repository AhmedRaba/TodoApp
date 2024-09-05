package com.training.todoapp.database.repos

import androidx.lifecycle.LiveData
import com.training.todoapp.database.dao.TodoDao
import com.training.todoapp.database.entity.Todo

class TodoRepository(private val todoDao: TodoDao) {


    val readAllData: LiveData<List<Todo>> = todoDao.getTodos()


    suspend fun addTodo(todo: Todo) {
        todoDao.addTodo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }


    suspend fun markTodoAsDone(todo: Todo) {
        val updatedTodo = todo.copy(isDone = true)
        todoDao.updateTodo(updatedTodo)
    }

    fun getTodosByDate(date: Long): List<Todo> {
        return todoDao.getTodosByDate(date)
    }


}