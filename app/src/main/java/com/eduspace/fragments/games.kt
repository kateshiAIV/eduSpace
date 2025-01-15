package com.eduspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.R
import com.eduspace.adapter.rvGamesAdapter
import com.eduspace.models.Game
import com.google.firebase.database.DatabaseReference

class games : Fragment() {

    private lateinit var gamesRecyclerView: RecyclerView
    private lateinit var gamesAdapter: rvGamesAdapter

    private val gamesList = ArrayList<Game>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games, container, false)

        // Initialize RecyclerView
        gamesRecyclerView = view.findViewById(R.id.games_recycler_view)
        gamesRecyclerView.layoutManager = LinearLayoutManager(requireContext())



        // Populate the games list
        gamesList.add(
            Game(
                gameName = "Space Adventure",
                gameDescription = "Explore the galaxy and face it creatures.",
                gamePic = null // Add a valid URL or resource if applicable
            )
        )
        gamesList.add(
            Game(
                gameName = "Time Traveller",
                gameDescription = "Travel in time.",
                gamePic = null // Add a valid URL or resource if applicable
            )
        )

        gamesList.add(
            Game(
                gameName = "Star puzzler",
                gameDescription = "Puzzle stars.",
                gamePic = null // Add a valid URL or resource if applicable
            )
        )

        // Set up the adapter
        gamesAdapter = rvGamesAdapter(gamesList)
        gamesRecyclerView.adapter = gamesAdapter

        return view
    }
}
