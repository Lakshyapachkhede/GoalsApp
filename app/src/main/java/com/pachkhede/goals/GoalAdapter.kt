package com.pachkhede.goals

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.pachkhede.goals.databinding.ItemGoalBinding

class GoalAdapter (
    private var goals : MutableList<Goal>,
    private val context : Context,
    private val onGoalDeleted: (Goal) -> Unit
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(val binding: ItemGoalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding  = ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GoalViewHolder(binding);
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.binding.goalText.text = goal.goal;
        holder.binding.goalCheckbox.isChecked = goal.isCompleted;

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context, R.style.CustomDialogStyle)
                .setTitle("Delete Goal")
                .setMessage("Are you sure you want to delete this goal?\n${goal.goal}")
                .setPositiveButton("Delete") { _, _ ->
                    deleteGoal(position)
                }
                .setNegativeButton("Cancel", null)
                .show()

            true
        }


        holder.binding.goalCheckbox.setOnCheckedChangeListener { _, isChecked ->
            goal.isCompleted = isChecked
            val dbHelper = DatabaseHelper(context)
            dbHelper.updateGoal(goal.id, goal.goal, goal.date, goal.isCompleted)
        }
    }

    fun updateGoals(newGoals : List<Goal>) {
        goals.clear()
        goals.addAll(newGoals)
        notifyDataSetChanged()
    }

    private fun deleteGoal(position: Int) {
        val goal = goals[position]
        goals.removeAt(position)
        notifyItemRemoved(position)
        onGoalDeleted(goal)
    }

}