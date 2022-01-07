package com.example.mvvmtest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvmtest.data.model.TodoModel

@Dao
interface TodoDAO {
    @Query("SELECT * FROM Todo ORDER BY createdDate")
    fun getTodoList(): LiveData<List<TodoModel>>

    @Insert
    fun insertTodo(todoModel: TodoModel)
}