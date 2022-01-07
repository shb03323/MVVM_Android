package com.example.mvvmtest.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtest.data.model.TodoModel
import com.example.mvvmtest.databinding.ItemTodoBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class TodoListAdapter: RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    private var todoList = mutableListOf<TodoModel>()

    interface OnTodoItemClickListener {
        fun onTodoItemClick(position: Int)
        fun onTodoItemLongClick(position: Int)
    }

    var listener: OnTodoItemClickListener? = null

    fun getItem(position: Int): TodoModel {
        return todoList[position]
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(todoList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return TodoViewHolder(binding, listener = null)
    }

    fun addItem(todoModel: TodoModel) {
        todoList.add(todoModel)
    }

    @SuppressLint("NotifyDataSetChanged", "CheckResult")
    fun setTodoItems(todoItems: MutableList<TodoModel>) {
        Observable
            .just(todoItems)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .map {
                DiffUtil.calculateDiff(TodoListDiffCallback(this.todoList, todoItems))
            }
            .subscribe({
                this.todoList = todoItems
                it.dispatchUpdatesTo(this)
                       }, {

        })
    }

    class TodoViewHolder(private val binding: ItemTodoBinding, listener: OnTodoItemClickListener?): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener?.onTodoItemClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                listener?.onTodoItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun onBind(model: TodoModel) {
            binding.tvTodoTitle.text = model.title
            binding.tvTodoDescription.text = model.description
            binding.tvTodoCreatedDate.text = model.createdDate.toDateString("yyyy.MM.dd HH:mm")
        }

        @SuppressLint("SimpleDateFormat")
        private fun Long.toDateString(string: String): String {
            val simpleDateFormat = SimpleDateFormat(string)
            return simpleDateFormat.format(Date(this))
        }
    }
}