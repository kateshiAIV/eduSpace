//package com.eduspace
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.eduspace.adapter.RvCommentAdapter
//import com.eduspace.databinding.ActivityLoginBinding
//import com.eduspace.databinding.ActivityPostDataBinding
//import com.eduspace.models.Comment
//import com.eduspace.models.PostData
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//
//class PostDataActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityPostDataBinding
//    private lateinit var rvComments: RecyclerView
//    private lateinit var authors: Array<String>
//    private lateinit var comms: Array<String>
//    private lateinit var commsL: ArrayList<Comment>
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Inflate the layout using ViewBinding
//        binding = ActivityPostDataBinding.inflate(layoutInflater)
//        setContentView(binding.root) // Ensure you're setting the correct root view
//
//        // Retrieve intent extras
//        val intent = intent
//        val header = intent?.getStringExtra("postHeader")
//        val url = intent?.getStringExtra("postLink")
//        val desc  = intent?.getStringExtra("postDescription")
//
//        // Set header text
//        binding.tvHeaderPost.text = header
//        binding.tvDescription.text = desc
//
//
//        val post1 = Comment(author = "author1", comment = "laalflelfaal3434eefe")
//        val post2 = Comment(author = "author2", comment = "laalf56lelfaaleefe")
//        val post3 = Comment(author = "author3", comment = "laalflelfaale133213123122331231233211efe")
//        val post4 = Comment(author = "author5", comment = "laalfle12312233lfaaleefe")
//        commsL.add(post1)
//        commsL.add(post2)
//        commsL.add(post3)
//        commsL.add(post4)
//
//        rvComments = findViewById(R.id.rvComments)
//
//        binding.rvComments.apply {
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(context)
//        }
//        getComments()
//        rvComments.adapter = RvCommentAdapter(commsL)
//
//
//
//                // Set click listener for the original link TextView
//        binding.tvOriginalLink.setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            startActivity(browserIntent)
//        }
//
//
//
//
//
//
//
//    }
//
//    private fun getComments() {
//        for (i in authors.indices){
//            val comment = Comment(authors[i], comms[i])
//            commsL.add(comment)
//        }
//    }
//}


package com.eduspace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduspace.adapter.RvCommentAdapter
import com.eduspace.adapter.RvPostsAdapter
import com.eduspace.databinding.ActivityPostDataBinding
import com.eduspace.models.Comment
import com.eduspace.models.PostData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PostDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDataBinding
    private lateinit var commsL: ArrayList<Comment>
    private lateinit var firebaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPostDataBinding.inflate(layoutInflater)
        setContentView(binding.root)





        val intent = intent
        val postID = intent?.getStringExtra("postID")
        val postHeader = intent?.getStringExtra("postHeader")
        val postAuthor = intent?.getStringExtra("postAuthor")
        val postLink = intent?.getStringExtra("postLink")
        val postLikes = intent?.getStringExtra("postLikes")
        val postDescription = intent?.getStringExtra("postDescription")
        val postComments = intent?.getStringExtra("postComments")
        val postSaves = intent?.getStringExtra("postSaves")
        val comments = intent?.getParcelableArrayListExtra<Comment>("postCommentsObj")
        commsL = ArrayList()
        commsL = comments ?: ArrayList()


        binding.tvHeaderPost.text = postHeader
        binding.tvDescription.text = postDescription
        firebaseRef = FirebaseDatabase.getInstance().getReference("posts")






        binding.rvComments.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PostDataActivity)
            adapter = RvCommentAdapter(commsL)
        }

        binding.tvOriginalLink.setOnClickListener {
            if (!postLink.isNullOrEmpty()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(postLink))
                startActivity(browserIntent)
            }
        }


        binding.btnSendComment.setOnClickListener {

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val currentUser = sharedPreferences.getString("currentUser", "Unknown User")


            val newCommentText = binding.etNewComment.text.toString().trim()


            if (newCommentText.isNotEmpty()) {

                val newComment = Comment(author = currentUser, comment = newCommentText)


                val updatedComments = (comments ?: ArrayList()).toMutableList()


                updatedComments.add(newComment)

                val updatedPost = PostData(
                    postID = postID,
                    postHeader = postHeader,
                    postAuthor = postAuthor,
                    postLink = postLink,
                    postLikes = postLikes,
                    postDescription = postDescription,
                    postCommentsObj = updatedComments,
                    postComments = postComments,
                    postSaves = postSaves
                )

                val updatedRef = firebaseRef.child(updatedPost.first())
                updatedRef.setValue(updatedPost)

                // Update the RecyclerView's adapter
//                (binding.rvComments.adapter as? RvCommentAdapter)?.apply {
//                    updateComments(updatedComments) // Assuming your adapter has an update method
//                }

                Toast.makeText(this, "Comment added: $updatedPost", Toast.LENGTH_SHORT).show()

                binding.etNewComment.text.clear()


                binding.rvComments.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@PostDataActivity)
                    adapter = RvCommentAdapter(updatedComments.filterNotNull() as ArrayList<Comment>)
                }




            } else {
                Toast.makeText(this, "Please write a comment before sending.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

