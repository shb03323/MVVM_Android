
package com.example.mvvmtest.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtest.data.model.TodoModel
import com.example.mvvmtest.databinding.ActivityMainBinding
import com.example.mvvmtest.databinding.DialogTodoBinding
import com.example.mvvmtest.ui.adapter.TodoListAdapter
import com.example.mvvmtest.viewmodel.TodoViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoListAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        initRecyclerView()
        initAddButton()
        initViewModel()
    }

    private fun initRecyclerView() {
        mTodoListAdapter = TodoListAdapter()
            .apply {
                listener = object: TodoListAdapter.OnTodoItemClickListener {
                    override fun onTodoItemClick(position: Int) {
                        openModifyTodoDialog(getItem(position))
                        Toast.makeText(this@MainActivity, "onTodoItemClick!", Toast.LENGTH_LONG).show()
                    }

                    override fun onTodoItemLongClick(position: Int) {
                        openDeleteTodoDialog(getItem(position))
                        Toast.makeText(this@MainActivity, "onLongTodoItemClick!", Toast.LENGTH_LONG).show()
                    }
                }
            }

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
        mTodoViewModel.getTodoList().observe(this) {
            mTodoListAdapter.setTodoItems(it as MutableList<TodoModel>)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openAddTodoDialog() {
        val dialogBinding = DialogTodoBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("????????????")
            .setView(dialogBinding.root)
            .setPositiveButton("??????") { _, _ ->
                val title = dialogBinding.etTodoTitle.text.toString()
                val description = dialogBinding.etTodoDescription.text.toString()
                val createdDate = Date().time

                val todoModel = TodoModel(null, title, description, createdDate)
                mTodoViewModel.insertTodo(todoModel)
            }
            .setNegativeButton("??????", null)
            .create()
        dialog.show()
    }

    private fun openModifyTodoDialog(todoModel: TodoModel) {
        val dialogBinding = DialogTodoBinding.inflate(layoutInflater)
        dialogBinding.etTodoTitle.setText(todoModel.title)
        dialogBinding.etTodoDescription.setText(todoModel.description)

        val dialog = AlertDialog.Builder(this)
            .setTitle("????????????")
            .setView(dialogBinding.root)
            .setPositiveButton("??????") { _, _ ->
                val title = dialogBinding.etTodoTitle.text.toString()
                val description = dialogBinding.etTodoDescription.text.toString()
                todoModel.description = description
                todoModel.title = title
                mTodoViewModel.updateTodo(todoModel)
            }
            .setNegativeButton("??????", null)
            .create()

        dialog.show()
    }

    private fun openDeleteTodoDialog(todoModel: TodoModel) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("????????????")
            .setMessage("????????? ????????? ???????????????.")
            .setPositiveButton("??????") { _, _ ->
                mTodoViewModel.deleteTodo(todoModel)
            }
            .setNegativeButton("??????", null)
            .create()

        dialog.show()
    }
}
