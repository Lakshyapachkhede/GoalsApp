package com.pachkhede.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pachkhede.goals.databinding.FragmentTodayBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GoalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        
        setupRecyclerView()
        setupFAB()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks
        _binding = null
    }

    private fun setupRecyclerView() {
        val dbHelper = DatabaseHelper(requireContext())
        val goals = dbHelper.getGoalsByDate(getTodayDate())

        adapter = GoalAdapter(goals, requireContext()) { goal ->
            deleteGoalFromDatabase(goal)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter;

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun setupFAB() {
        binding.goalAddFAB.setOnClickListener {
            showAddGoalDialog()
        }
    }

    private fun showAddGoalDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_goal, null)
        val input = dialogView.findViewById<EditText>(R.id.goalEditText)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialogStyle)
            .setTitle("Add New Goal")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val goalText = input.text.toString().trim()
                if (goalText.isNotEmpty()) {
                    addGoal(goalText)
                } else {
                    Toast.makeText(requireContext(), "Goal cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun addGoal(goalText : String) {
        val dbHelper = DatabaseHelper(requireContext())
        dbHelper.insertGoal(goalText, getTodayDate(), false)

        refreshGoals()
    }

    private fun getTodayDate(): String {
        val currentDate = LocalDate.now() // Get today's date
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Define the desired format
        return currentDate.format(formatter) // Format the date
    }


    private fun deleteGoalFromDatabase(goal : Goal) {
        val dbHelper = DatabaseHelper(requireContext())
        dbHelper.deleteGoal(goal.id)
        refreshGoals()
    }

    private fun refreshGoals() {
        val dbHelper = DatabaseHelper(requireContext())
        val goals = dbHelper.getGoalsByDate(getTodayDate()) // Get updated goals
        adapter.updateGoals(goals)
    }


}
