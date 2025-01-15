package com.eduspace.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduspace.MainActivity
import com.eduspace.R
import com.eduspace.SuggestPostActivity
import com.eduspace.SuggestedPostsActivity
import com.eduspace.adapter.RvPostsAdapter
import com.eduspace.databinding.FragmentNewsBinding
import com.eduspace.models.Comment
import com.eduspace.models.PostData
import com.eduspace.models.QuizData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class news : Fragment() {


    private lateinit var suggestedImg: ImageButton
    private lateinit var suggestImg: ImageButton
    private lateinit var savedImg: ImageButton

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var postList: ArrayList<PostData>
    private lateinit var savedPostList: ArrayList<PostData>
    private lateinit var firebaseRef: DatabaseReference

    private var showingSavedPosts = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val currentUserRole = sharedPreferences.getString("currentUserRole", "user")




        suggestedImg = binding.suggestedImg
        suggestImg = binding.suggestImg
        savedImg = binding.savedImg


        if (currentUserRole == "admin"){
            suggestedImg.visibility = View.VISIBLE
        }else {

            suggestedImg.visibility = View.GONE

        }
        suggestedImg.setOnClickListener {
            val intent = Intent(requireContext(), SuggestedPostsActivity::class.java)
            startActivity(intent)
        }



        suggestImg.setOnClickListener {
            val intent = Intent(requireContext(), SuggestPostActivity::class.java)
            startActivity(intent)
        }

        savedImg.setOnClickListener {
            showingSavedPosts = !showingSavedPosts


            loadPosts { posts ->
                postList.clear()
                postList.addAll(posts)
                updateSavedPosts()
                updatePostView()
            }
        }

        binding.rvPosts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        postList = arrayListOf()
        savedPostList = arrayListOf()
        firebaseRef = FirebaseDatabase.getInstance().getReference("posts")

        loadPosts { posts ->
            postList.clear()
            postList.addAll(posts)
            updateSavedPosts()
            updatePostView()
        }

        return binding.root
    }

    private fun loadPosts(onComplete: (List<PostData>) -> Unit) {
        firebaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<PostData>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(PostData::class.java)
                    if (post != null) posts.add(post)
                }
                onComplete(posts)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch posts: ${error.message}", Toast.LENGTH_SHORT).show()
                onComplete(emptyList())
            }
        })
    }

    private fun updateSavedPosts() {
        val savedIds = requireContext()
            .getSharedPreferences("SAVED_POSTS_PREF", Context.MODE_PRIVATE)
            .getStringSet("SAVED_POSTS", emptySet())
            ?.map { it.toInt() }
            ?: emptyList()

        savedPostList.clear()
        savedPostList.addAll(postList.filter { it.postID?.toInt() in savedIds })
    }

    private fun updatePostView() {






        val adapter = if (showingSavedPosts) {
            savedImg.setImageResource(R.drawable.save2)
            RvPostsAdapter(savedPostList, requireContext())
        } else {

            savedImg.setImageResource(R.drawable.save1)
            RvPostsAdapter(postList, requireContext())
        }
        binding.rvPosts.adapter = adapter

        Toast.makeText(
            context,
            if (showingSavedPosts) "Showing saved posts" else "Showing all posts",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
