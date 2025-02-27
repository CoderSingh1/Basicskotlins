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

    private lateinit var todoAdapter : TodoAdapter
    private lateinit var taskList: MutableList<Todo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        todoAdapter = TodoAdapter(mutableListOf())

        val taskRV = findViewById<RecyclerView>(R.id.taskRV)
        val addButton = findViewById<Button>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val taskET = findViewById<EditText>(R.id.taskET)


        taskRV.adapter = todoAdapter

        taskRV.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener{
            if(taskList.isNotEmpty()){
                taskList.add(Todo(taskET.text.toString(),false))
                TodoAdapter(taskList)
            }
        }

        deleteButton.setOnClickListener {
            todoAdapter.deleteTodo()
        }



        }
    }