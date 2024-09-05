package com.training.todoapp.frags

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.training.todoapp.TodosAdapter
import com.training.todoapp.database.entity.Todo
import com.training.todoapp.database.viewmodel.TodoViewModel
import com.training.todoapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<TodoViewModel>()
    private lateinit var adapter: TodosAdapter
    private var selectedDay = CalendarDay.today()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initCalendarListener()
        observeTodosByDate()
        refreshTodos()

    }

    private fun initCalendarListener() {
        binding.calendarView.currentDate = CalendarDay.today()
        binding.calendarView.selectedDate = CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDay = date
            refreshTodos()
        }

    }

    fun refreshTodos() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, selectedDay.year)
        calendar.set(Calendar.MONTH, selectedDay.month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay.day)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)


        viewModel.getTodosByDate(calendar.timeInMillis)


    }

    private fun initRecyclerView() {
        adapter = TodosAdapter()

        adapter.listener = object : TodosAdapter.OnItemClickListener {
            override fun onDeleteClick(todo: Todo) {
                viewModel.deleteTodo(todo)
                refreshTodos()
            }

            override fun onDoneClick(todo: Todo) {
                viewModel.markTodoAsDone(todo)
                refreshTodos()
            }

            override fun onItemClick(todo: Todo) {
                val action= ListFragmentDirections.actionListFragmentToTaskDetailsFragment(todo)
                findNavController().navigate(action)
            }
        }
        binding.rvTodo.adapter = adapter
    }

    private fun observeTodosByDate() {
        refreshTodos()
        viewModel.todosForSelectedDate.observe(viewLifecycleOwner) {
            Log.e("observeTodosByDate", "observeTodosByDate: $it")
            adapter.setData(it)
        }
    }
}
