package com.eduspace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.R
import com.eduspace.models.Comment



//avtor i comment i rv dla Commentov


class RvCommentAdapter(private val  commentList: ArrayList<Comment>) : RecyclerView.Adapter<RvCommentAdapter.ViewHolder>() {
     inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

         val author = itemView.findViewById<TextView>(R.id.tvUser)
         val comment = itemView.findViewById<TextView>(R.id.tvComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentComment = commentList[position]
        holder.author.text = currentComment.author
        holder.comment.text = currentComment.comment
    }
}