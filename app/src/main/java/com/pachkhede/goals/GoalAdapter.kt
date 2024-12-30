package com.pachkhede.goals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pachkhede.goals.databinding.ItemGoalBinding

class GoalAdapter (
    private val goals : List<Goal>
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


        holder.binding.goalCheckbox.setOnCheckedChangeListener { _, isChecked ->
            goal.isCompleted = isChecked
        }
    }

}