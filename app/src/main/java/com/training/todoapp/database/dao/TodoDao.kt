package com.training.todoapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.training.todoapp.database.entity.Todo
@Dao
interface TodoDao {

    @Insert
    suspend fun addTodo(todo:Todo)

    @Delete
    suspend fun deleteTodo(todo:Todo)

    @Update
    suspend fun updateTodo(todo:Todo)

    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getTodos():LiveData<List<Todo>>

}