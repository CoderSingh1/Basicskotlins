package com.example.todo_list

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todos: MutableList<Todo>
  ) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val cbDone : CheckBox = itemView.findViewById(R.id.cbDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_items,
                parent,
                false
            )
        )
    }

    fun addTodo(todo : Todo){
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun deleteTodo(){
        todos.removeAll { todo ->  todo.isChecked}
        notifyDataSetChanged()
    }


    private fun strikeThroughToggle(taskTitle : TextView, isChecked : Boolean){
        if(isChecked){
            taskTitle.paintFlags = taskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else{
            taskTitle.paintFlags = taskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo =  todos[position]
        holder.itemView.apply {
            holder.taskTitle.text = curTodo.title
            holder.cbDone.isChecked = curTodo.isChecked
            strikeThroughToggle(holder.taskTitle, curTodo.isChecked)
            holder.cbDone.setOnCheckedChangeListener { _, isChecked ->
                strikeThroughToggle(holder.taskTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }

        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}