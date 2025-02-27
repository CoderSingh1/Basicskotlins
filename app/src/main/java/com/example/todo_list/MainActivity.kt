package com.example.todo_list

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

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private var taskList = mutableListOf<Todo>()

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

        taskRV.layoutManager = LinearLayoutManager(this)

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
    }
}