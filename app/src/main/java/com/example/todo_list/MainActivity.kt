package com.example.todo_list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private var taskList = mutableListOf<Todo>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val taskRV = findViewById<RecyclerView>(R.id.taskRV)
        val addButton = findViewById<Button>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val taskET = findViewById<EditText>(R.id.taskET)

        sharedPreferences = getSharedPreferences("TodoPrefs", Context.MODE_PRIVATE)

        taskList = loadTasks()
        todoAdapter = TodoAdapter(taskList)
        taskRV.adapter = todoAdapter
        taskRV.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val taskText = taskET.text.toString().trim()
            if (taskText.isNotEmpty()) {
                val todo = Todo(taskText, false)
                taskList.add(todo)
                todoAdapter.notifyItemInserted(taskList.size - 1)
                taskET.text.clear()
                saveTasks()
            }
        }

        deleteButton.setOnClickListener {
            todoAdapter.deleteTodo()
            saveTasks()
        }
    }

    private fun saveTasks() {
        val editor = sharedPreferences.edit()
        val jsonArray = JSONArray()

        for (task in taskList) {
            jsonArray.put(task.title)
        }

        editor.putString("tasks", jsonArray.toString())
        editor.apply()
    }

    private fun loadTasks(): MutableList<Todo> {
        val json = sharedPreferences.getString("tasks", null) ?: return mutableListOf()
        val jsonArray = JSONArray(json)

        val savedTasks = mutableListOf<Todo>()
        for (i in 0 until jsonArray.length()) {
            savedTasks.add(Todo(jsonArray.getString(i), false))
        }

        return savedTasks
    }
}