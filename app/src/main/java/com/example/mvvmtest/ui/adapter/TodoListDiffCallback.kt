package com.example.mvvmtest.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mvvmtest.data.model.TodoModel

class TodoListDiffCallback(val oldTodoList: List<TodoModel>, val newTodoList: List<TodoModel>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodoList[oldItemPosition].id == newTodoList[newItemPosition].id
    }

    override fun getOldListSize() = oldTodoList.size

    override fun getNewListSize() = newTodoList.size

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newTodoList[newItemPosition] == oldTodoList[oldItemPosition]
    }
}
