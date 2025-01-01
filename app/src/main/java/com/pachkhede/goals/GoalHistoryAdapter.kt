package com.pachkhede.goals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalHistoryAdapter(private val historyList: List<GoalHistory>) : RecyclerView.Adapter<GoalHistoryAdapter.GoalHistoryViewHolder>(){

    class GoalHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDay : TextView = itemView.findViewById(R.id.tvDay)
        val tvMonthYear : TextView = itemView.findViewById(R.id.tvMonthYear)
        val tvPercentage : TextView = itemView.findViewById(R.id.tvPercentage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_goal, parent, false)
        return GoalHistoryViewHolder(view)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: GoalHistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.tvDay.text = item.day
        holder.tvMonthYear.text = item.monthYear

        val percentage : String = item.percentage.toString() + "%"
        holder.tvPercentage.text = percentage


    }


}