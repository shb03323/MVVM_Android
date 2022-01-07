
package com.example.mvvmtest.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtest.data.model.TodoModel
import com.example.mvvmtest.databinding.ActivityMainBinding
import com.example.mvvmtest.databinding.DialogTodoBinding
import com.example.mvvmtest.viewmodel.TodoViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoListAdapter: TodoListAdapter
//    private val mTodoItems: ArrayList<TodoModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        mTodoItems.run {
//            add(TodoModel("안드로이드 포스팅1", "MVVM - 1", Date().time))
//            add(TodoModel("안드로이드 포스팅1", "MVVM - 2", Date().time))
//            add(TodoModel("안드로이드 포스팅1", "MVVM - 3", Date().time))
//        }

        initRecyclerView()
        initAddButton()
        initViewModel()
    }

    private fun initRecyclerView() {
        mTodoListAdapter = TodoListAdapter()

        binding.rlTodoList.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mTodoListAdapter
        }
    }

    private fun initAddButton() {
        binding.btnAddTodo.setOnClickListener {
            openAddTodoDialog()
        }
    }

    private fun initViewModel() {
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(TodoViewModel::class.java)
        mTodoViewModel.getTodoList().observe(this, {
            mTodoListAdapter.setTodoItems(it as MutableList<TodoModel>)
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openAddTodoDialog() {
        val dialogBinding = DialogTodoBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("추가하기")
            .setView(dialogBinding.root)
            .setPositiveButton("확인") { _, _ ->
                val title = dialogBinding.etTodoTitle.text.toString()
                val description = dialogBinding.etTodoDescription.text.toString()
                val createdDate = Date().time

                val todoModel = TodoModel(null, title, description, createdDate)
                mTodoViewModel.insertTodo(todoModel)
            }
            .setNegativeButton("취소", null)
            .create()
        dialog.show()
    }
}
