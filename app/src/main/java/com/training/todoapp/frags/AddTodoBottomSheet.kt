package com.training.todoapp.frags

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.training.todoapp.database.entity.Todo
import com.training.todoapp.database.viewmodel.TodoViewModel
import com.training.todoapp.databinding.BottomSheetAddTodoBinding
import java.util.Calendar

class AddTodoBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAddTodoBinding
    private var calendar = Calendar.getInstance()
    private val viewModel: TodoViewModel by activityViewModels()
    private var onDismissListener: (() -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetAddTodoBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateDateTv()


        initDatePickerDialog()

        initListeners()


    }

    private fun initListeners() {
        binding.btnAddTodo.setOnClickListener {
            if (isValidInput()) {
                val title = binding.etTodoTitle.text.toString()
                val description = binding.etTodoDescription.text.toString()
                val newTodo =
                    Todo(
                        title = title,
                        description = description,
                        date = calendar.timeInMillis,
                        isDone = false
                    )
                Log.e("initListeners", "initListeners: ${calendar.timeInMillis}")
                viewModel.addTodo(newTodo)
                dismiss()
            }
        }

    }

    private fun isValidInput(): Boolean {
        val title = binding.etTodoTitle.text
        val description = binding.etTodoDescription.text

        binding.etTodoTitle.error = null
        binding.etTodoDescription.error = null

        var isValid = true

        if (title.isNullOrEmpty()) {
            binding.etTodoTitle.error = "Title is required"
            isValid = false
        }
        if (description.isNullOrEmpty()) {
            binding.etTodoDescription.error = "Description is required"
            isValid = false
        }
        return isValid
    }


    private fun initDatePickerDialog() {
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        binding.tvTodoDate.setOnClickListener {
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
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        Log.e("HELPPP", "HELPP2: ${calendar.timeInMillis}")
        binding.tvTodoDate.text = "$day/${month + 1}/$year"
    }

    fun setOnDismissListener(listener: () -> Unit) {
        onDismissListener = listener
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }
}