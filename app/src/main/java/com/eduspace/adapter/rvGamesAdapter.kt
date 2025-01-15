package com.eduspace.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.LeaderBoard
import com.eduspace.PostDataActivity
import com.eduspace.R
import com.eduspace.SpaceAdventure
import com.eduspace.TimeActivity
import com.eduspace.models.Comment
import com.eduspace.models.Game




class rvGamesAdapter (private val  gamesList: ArrayList<Game>) : RecyclerView.Adapter<rvGamesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val game_name = itemView.findViewById<TextView>(R.id.game_name)
        val game_description = itemView.findViewById<TextView>(R.id.game_description)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.games_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGame = gamesList[position]
        holder.game_name.text = currentGame.gameName
        holder.game_description.text = currentGame.gameDescription

        holder.itemView.setOnClickListener{
            when (currentGame.gameName) {
                "Space Adventure" -> {
                    val intent = Intent(it.context, SpaceAdventure::class.java)
                    it.context.startActivity(intent)
                }

                "Time Traveller" -> {
                    val intent = Intent(it.context, TimeActivity::class.java)
                    it.context.startActivity(intent)
                }

                "Space Adventure" -> {
                    val intent = Intent(it.context, SpaceAdventure::class.java)
                    it.context.startActivity(intent)
                }
                else ->  {
                    Toast.makeText(it.context, "Coming soon...", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}