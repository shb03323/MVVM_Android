package com.example.mvvmtest.data.repository

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mvvmtest.data.database.TodoDAO
import com.example.mvvmtest.data.database.TodoDatabase
import com.example.mvvmtest.data.model.TodoModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class TodoRepository(application: Application) {
    private var mTodoDatabase: TodoDatabase
    private var mTodoDAO: TodoDAO
    private var mTodoItems: LiveData<List<TodoModel>>

    init {
        mTodoDatabase = TodoDatabase.getInstance(application)
        mTodoDAO = mTodoDatabase.todoDao()
        mTodoItems = mTodoDAO.getTodoList()
    }

    fun getTodoList(): LiveData<List<TodoModel>> {
        return mTodoItems
    }

    @SuppressLint("CheckResult")
    fun insertTodo(todoModel: TodoModel) {
        Observable.just(todoModel)
            .subscribeOn(Schedulers.io())
            .subscribe({
                mTodoDAO.insertTodo(todoModel)
            }, {
            // Handle error.
            })

    }
}
