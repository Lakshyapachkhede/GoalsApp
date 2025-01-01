package com.pachkhede.goals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.pachkhede.goals.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        val dbHelper = DatabaseHelper(requireContext())
        val historyData = fetchHistoryData(dbHelper)

        val adapter = GoalHistoryAdapter(historyData)
        binding.goalHistoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.goalHistoryRecyclerView.adapter = adapter
    }

    private fun fetchHistoryData(dbHelper: DatabaseHelper): List<GoalHistory> {
        val historyList = mutableListOf<GoalHistory>()

        val allDates = dbHelper.getAllDates()
        for (date in allDates) {
            val totalGoals = dbHelper.getTotalGoalsByDate(date)
            val completedGoals = dbHelper.getCompletedGoalsByDate(date)

            val percentage = if (totalGoals > 0) (completedGoals * 100) / totalGoals else 0

            // Parse the date into desired format
            val dateParts = date.split("-") // Assuming date format is YYYY-MM-DD
            val day = dateParts[2] // Extract day
            val monthYear = "${getMonthAbbreviation(dateParts[1])} ${dateParts[0]}" // Format as "Mon YYYY"

            historyList.add(GoalHistory(day, monthYear, percentage))
        }

        return historyList
    }

    // Helper function to get month abbreviation
    private fun getMonthAbbreviation(month: String): String {
        return when (month.toInt()) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }
    }




}