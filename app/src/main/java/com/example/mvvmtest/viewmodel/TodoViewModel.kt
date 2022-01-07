package com.example.mvvmtest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mvvmtest.data.model.TodoModel
import com.example.mvvmtest.data.repository.TodoRepository

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val mTodoRepository: TodoRepository
    private var mTodoItems: LiveData<List<TodoModel>>

    init {
        mTodoRepository = TodoRepository(application)
        mTodoItems = mTodoRepository.getTodoList()
    }

    fun insertTodo(todoModel: TodoModel) {
        mTodoRepository.insertTodo(todoModel)
    }

    fun getTodoList(): LiveData<List<TodoModel>> {
        return mTodoItems
    }

    fun updateTodo(todoModel: TodoModel) {
        mTodoRepository.updateTodo(todoModel)
    }

    fun deleteTodo(todoModel: TodoModel) {
        mTodoRepository.deleteTodo(todoModel)
    }
}
