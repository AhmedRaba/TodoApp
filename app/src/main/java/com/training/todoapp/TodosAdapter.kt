package com.training.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.todoapp.database.entity.Todo
import com.training.todoapp.databinding.ItemTodoBinding

class TodosAdapter(
) :
    RecyclerView.Adapter<TodosAdapter.TodoViewHolder>() {

    private var todos = listOf<Todo>()

    inner class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = todos[position]
        holder.binding.apply {
            tvTodoTitle.text = currentItem.title
            tvTodoDescription.text = currentItem.description
            leftView.setOnClickListener {
                listener?.onDeleteClick(currentItem)
            }


            btnTodoDone.setOnClickListener {
                listener?.onDoneClick(currentItem)
                if (currentItem.isDone) {
                    tvTodoTitle.setTextColor(root.context.getColor(android.R.color.holo_green_dark))
                    tvTodoDescription.setTextColor(root.context.getColor(android.R.color.holo_green_dark))
                }
            }

            cardView.setOnClickListener {
                listener?.onItemClick(currentItem)
            }


        }
    }

    fun setData(todos: List<Todo>) {
        this.todos = todos
        notifyDataSetChanged()
    }


    var listener: OnItemClickListener? = null

    interface OnItemClickListener {

        fun onDeleteClick(todo: Todo)

        fun onDoneClick(todo: Todo)

        fun onItemClick(todo: Todo)

    }


}