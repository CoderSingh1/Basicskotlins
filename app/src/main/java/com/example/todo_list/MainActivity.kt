package com.example.todo_list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private var taskList = mutableListOf<Todo>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val taskRV = findViewById<RecyclerView>(R.id.taskRV)
        val addButton = findViewById<Button>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val taskET = findViewById<EditText>(R.id.taskET)


        todoAdapter = TodoAdapter(taskList)
        taskRV.adapter = todoAdapter
        sharedPreferences = getSharedPreferences("TodoPrefs", Context.MODE_PRIVATE)
        taskRV.layoutManager = LinearLayoutManager(this)

        //taskList = loadTasks()


        addButton.setOnClickListener {
            val taskText = taskET.text.toString().trim()
            if (taskText.isNotEmpty()) {
                val todo = Todo(taskText, false)
                todoAdapter.addTodo(todo)  // Use the adapter's function to add the task
                taskET.text.clear()
            }
        }

        deleteButton.setOnClickListener {
            todoAdapter.deleteTodo()
        }

        fun saveTasks() {
            val editor = sharedPreferences.edit()
            val jsonArray = JSONArray()

            for (task in taskList) {
                //jsonArray.put(task.task)  // Save only the task text
            }

            editor.putString("tasks", jsonArray.toString())
            editor.apply()
        }


        // Load task list from SharedPreferences
        fun loadTasks(): MutableList<Todo> {
            val json = sharedPreferences.getString("tasks", null) ?: return mutableListOf()
            val jsonArray = JSONArray(json)

            val savedTasks = mutableListOf<Todo>()
            for (i in 0 until jsonArray.length()) {
                savedTasks.add(Todo(jsonArray.getString(i), false))
            }

            return savedTasks
        }
    }
}