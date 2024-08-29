package com.training.todoapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.training.todoapp.databinding.BottomSheetAddTodoBinding
import java.util.Calendar

class AddTodoBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAddTodoBinding
    private var calendar = Calendar.getInstance()

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

        initDatePickerDialog()

        binding.btnAddTodo.setOnClickListener {
            isValidInput()
        }

    }

    private fun isValidInput(): Boolean {
        val title = binding.etTodoTitle.text
        val description = binding.etTodoDescription.text

        binding.etTodoTitle.error = null
        binding.etTodoDescription.error = null

        var isValid=true

        if (title.isNullOrEmpty()) {
            binding.etTodoTitle.error = "Title is required"
            isValid =false
        }
        if (description.isNullOrEmpty()) {
            binding.etTodoDescription.error = "Description is required"
            isValid =false
        }
        return isValid
    }


    private fun initDatePickerDialog() {
        binding.tvTodoDate.setOnClickListener {
            val dateDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
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
        binding.tvTodoDate.text = "$day/${month + 1}/$year"
    }


}