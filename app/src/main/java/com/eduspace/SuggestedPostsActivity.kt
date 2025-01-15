package com.eduspace

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduspace.adapter.rvSuggestedPostsAdapter
import com.eduspace.databinding.ActivitySuggestedPostsBinding
import com.eduspace.models.PostData
import com.eduspace.models.SuggestPost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.properties.Delegates

class SuggestedPostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestedPostsBinding
    private lateinit var postList: ArrayList<SuggestPost>
    private lateinit var adapter: rvSuggestedPostsAdapter
    private lateinit var firebaseRefSugg: DatabaseReference
    private lateinit var firebaseRefPosts: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestedPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postList = ArrayList()
        adapter = rvSuggestedPostsAdapter(postList)
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        binding.rvPosts.adapter = adapter

        firebaseRefSugg = FirebaseDatabase.getInstance().getReference("suggested")




            loadPosts { posts ->
                postList.clear()
                postList.addAll(posts)
                adapter = rvSuggestedPostsAdapter(postList) // Pass updated count
                binding.rvPosts.adapter = adapter
            }



    }

    private fun loadPosts(onComplete: (List<SuggestPost>) -> Unit) {
        firebaseRefSugg.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<SuggestPost>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(SuggestPost::class.java)
                    if (post != null) posts.add(post)
                }
                onComplete(posts)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@SuggestedPostsActivity,
                    "Failed to fetch posts: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
                onComplete(emptyList())
            }
        })
    }



    private fun loadPostsTab(onComplete: (Int) -> Unit) {
        firebaseRefPosts.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<PostData>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(PostData::class.java)
                    if (post != null) posts.add(post)
                }
                onComplete(posts.size) // Pass count
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@SuggestedPostsActivity,
                    "Failed to fetch posts: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
                onComplete( 0) // Pass 0 count
            }
        })
    }




}

