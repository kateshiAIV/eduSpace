package com.eduspace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.R
import com.eduspace.models.QuizData

class RvLeaderBoardAdapter(private val leadersList: List<QuizData>) : RecyclerView.Adapter<RvLeaderBoardAdapter.ViewHolder>() {


    //User i Score dla Scoreborda
    
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leader = itemView.findViewById<TextView>(R.id.tvLeader)
        val score = itemView.findViewById<TextView>(R.id.tvScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.leaders_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return leadersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentLeader = leadersList[position]
        holder.leader.text = currentLeader.userName
        holder.score.text = currentLeader.userRecord.toString()
    }
}
