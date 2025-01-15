package com.eduspace.fragments.starMapFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.R

class constellations_list : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_constellations_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // constellations
        val constellations = listOf(
            Constellation("Andromeda", R.drawable.andromeda),
            Constellation("Orion", R.drawable.orion),
            Constellation("Cassiopeia", R.drawable.cassiopeia),
            Constellation("Cygnus", R.drawable.cygnus),
            Constellation("Draco", R.drawable.draco),
            Constellation("Gemini", R.drawable.gemini),
            Constellation("Leo", R.drawable.leo),
            Constellation("Pegasus", R.drawable.pegasus),
            Constellation("Scorpius", R.drawable.scorpius),
            Constellation("Taurus", R.drawable.taurus)
        )

        val adapter = ConstellationsAdapter(constellations)
        recyclerView.adapter = adapter

        return view
    }
}
