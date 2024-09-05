package com.training.todoapp.frags

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.training.todoapp.database.entity.Todo
import com.training.todoapp.database.viewmodel.TodoViewModel
import com.training.todoapp.databinding.FragmentTaskDetailsBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TaskDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailsBinding
    private var calendar = Calendar.getInstance()
    private val viewModel by viewModels<TodoViewModel>()
    private val args by navArgs<TaskDetailsFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.etTaskTitle.setText(args.todo.title)
        binding.etTaskDescription.setText(args.todo.description)

        setupDate()
        initDatePicker()
        updateTodo()


    }


    private fun updateTodo() {

        binding.btnSaveEdit.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.etTaskDescription.text.toString()
            val newTodo = Todo(
                id = args.todo.id,
                title = title,
                description = description,
                date = calendar.timeInMillis,
                isDone = args.todo.isDone
            )

            viewModel.updateTodo(newTodo)
            findNavController().navigateUp()
        }

    }

    private fun initDatePicker() {

        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        binding.tvTaskDate.setOnClickListener {
            val dateDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    Log.e("HELPPP", "HELPP1: ${calendar.timeInMillis}")
                    updateDateTv()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

            )
            dateDialog.datePicker.minDate = calendar.timeInMillis

            dateDialog.show()
        }

    }

    private fun updateDateTv() {
        binding.tvTaskDate.text =
            "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                calendar.get(Calendar.YEAR)
            }"
    }

    private fun setupDate() {
        val date = Date(args.todo.date)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        binding.tvTaskDate.text = formattedDate
    }

}