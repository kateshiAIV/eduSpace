package com.eduspace.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.R
import com.eduspace.models.PostData
import com.eduspace.models.QuizData
import com.eduspace.models.SuggestPost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class rvSuggestedPostsAdapter(private val posts: List<SuggestPost>) :
    RecyclerView.Adapter<rvSuggestedPostsAdapter.PostViewHolder>() {
    private lateinit var firebaseRefSugg: DatabaseReference
    private lateinit var firebaseRefPosts: DatabaseReference

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvPostHeader: TextView = itemView.findViewById(R.id.tvHeader)
        val tvPostDescription: TextView = itemView.findViewById(R.id.tvDisc)
        val tvPostAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val imgPost: ImageButton = itemView.findViewById(R.id.imgPost)
        val imgDecline: ImageButton = itemView.findViewById(R.id.imgDecline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.suggested_post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.tvPostHeader.text = post.postHeader
        holder.tvPostDescription.text = post.postDescription
        holder.tvPostAuthor.text = post.postAuthor ?: "Anonymous"


        holder.imgPost.setOnClickListener {

            firebaseRefSugg = FirebaseDatabase.getInstance().getReference("suggested")
            firebaseRefPosts = FirebaseDatabase.getInstance().getReference("posts")

            val newPostID = generatePostID()
            val userRef = firebaseRefPosts.child(newPostID)

            val newPost = PostData(
                postID = (newPostID),
                postHeader = post.postHeader,
                postAuthor = post.postAuthor,
                postLink = post.postLink,
                postLikes = "0",
                postDescription = post.postDescription,
                postCommentsObj = null,
                postComments = "0",
                postSaves = "0"
            )

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        userRef.setValue(newPost)
                    } else {

                        userRef.setValue(newPost)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.")
                }
            })


            val database = Firebase.database

// Get a reference to the "suggested" node
            val suggestedRef = database.getReference("suggested")

// Get a reference to the specific record
            val recordRef = suggestedRef.child(post.postID.toString())

// Delete the record
            recordRef.removeValue()
                .addOnSuccessListener {
                    // Record deleted successfully
                    Log.d("Firebase", "Record deleted successfully")
                }
                .addOnFailureListener {
                    // Handle deletion error
                    Log.w("Firebase", "Failed to delete record.", it)
                }


            Toast.makeText(
                holder.itemView.context,
                "Post Approved: \nHeader: ${post.postHeader}\nDescription: ${post.postDescription}\nAuthor: ${post.postAuthor ?: "Anonymous"}",
                Toast.LENGTH_SHORT
            ).show()
        }



            holder.imgDecline.setOnClickListener {
                // Get a reference to your database
                val database = Firebase.database

// Get a reference to the "suggested" node
                val suggestedRef = database.getReference("suggested")

// Get a reference to the specific record
                val recordRef = suggestedRef.child(post.postID.toString())

// Delete the record
                recordRef.removeValue()
                    .addOnSuccessListener {
                        // Record deleted successfully
                        Log.d("Firebase", "Record deleted successfully")
                    }
                    .addOnFailureListener {
                        // Handle deletion error
                        Log.w("Firebase", "Failed to delete record.", it)
                    }



                Toast.makeText(
                    holder.itemView.context,
                    "Post Declined: \nHeader: ${post.postHeader}\nDescription: ${post.postDescription}\nAuthor: ${post.postAuthor ?: "Anonymous"}",
                    Toast.LENGTH_SHORT
                ).show()
            }




    }
    private fun generatePostID(): String {
        val dateFormat = SimpleDateFormat("MMddmmss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun getItemCount(): Int = posts.size
}