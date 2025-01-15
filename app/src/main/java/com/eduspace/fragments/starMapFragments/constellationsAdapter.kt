package com.eduspace.fragments.starMapFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.R

data class Constellation(val name: String, val imageResId: Int)

class ConstellationsAdapter(private val constellations: List<Constellation>) :
    RecyclerView.Adapter<ConstellationsAdapter.ConstellationViewHolder>() {

    class ConstellationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstellationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_constellations, parent, false)
        return ConstellationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConstellationViewHolder, position: Int) {
        val constellation = constellations[position]
        holder.textView.text = constellation.name
        holder.imageView.setImageResource(constellation.imageResId)
    }

    override fun getItemCount(): Int = constellations.size
}
