//package com.eduspace.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.eduspace.R
//import com.eduspace.databinding.PostItemBinding
//import com.eduspace.models.PostData
//
//class RvPostsAdapter(private val postList: java.util.ArrayList<PostData>) : RecyclerView.Adapter<RvPostsAdapter.ViewHolder>() {
//
//    class ViewHolder(val binding : PostItemBinding): RecyclerView.ViewHolder(binding.root) {
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
//    }
//
//    override fun getItemCount(): Int {
//        return postList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val likedPosts =  arrayListOf(1,2,3,4,5,7)
//        val currentItem = postList[position]
//        holder.apply {
//            binding.apply {
//                if(likedPosts.contains(currentItem.first().toInt())){
//                    imgUpvotes.setImageResource(R.drawable.like2)
//                }
//                tvHeader.text = currentItem.postHeader
//                tvDisc.text = currentItem.postLink
//                tvUpvotes.text = currentItem.postLikes
//                tvSaved.text = currentItem.postSaves
//
//                imgUpvotes.setOnClickListener {
//                    imgUpvotes.setImageResource(R.drawable.like2)
//
//                    showToast(it.context, currentItem.first(), "Upvote")
//                }
//
//
//
//                imgSaved.setOnClickListener {
//                    showToast(it.context, currentItem.first(), "Save")
//                }
//
//
//            }
//
//
//
//
//        }
//    }
//
//
//    private fun showToast(context: Context, postId: String, buttonType: String) {
//        val message = "Post ID: $postId - Button clicked: $buttonType"
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
//
//}

package com.eduspace.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.PostDataActivity
import com.eduspace.R
import com.eduspace.databinding.PostItemBinding
import com.eduspace.models.Comment
import com.eduspace.models.PostData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RvPostsAdapter(
    private val postList: ArrayList<PostData>,
    private val context: Context

) : RecyclerView.Adapter<RvPostsAdapter.ViewHolder>() {


    private val likedPosts: MutableSet<Int> = mutableSetOf()
    private val savedPosts: MutableSet<Int> = mutableSetOf()
    private lateinit var firebaseRef : DatabaseReference


    private val preferences = context.getSharedPreferences("LIKED_POSTS_PREF", Context.MODE_PRIVATE)
    private val preferences1 = context.getSharedPreferences("SAVED_POSTS_PREF", Context.MODE_PRIVATE)

    init {
        //zagruzaju iz pamiati
        loadSavedPosts()
        loadLikedPosts()
    }

    class ViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = postList[position]
        holder.apply {
            binding.apply {
                val postId = currentItem.first().toInt()


                //lajki i zapisi(kartinki)

                if (likedPosts.contains(postId)) {
                    imgUpvotes.setImageResource(R.drawable.like2)
                } else {
                    imgUpvotes.setImageResource(R.drawable.like1)
                }
                if (savedPosts.contains(postId)) {
                    imgSaved.setImageResource(R.drawable.save2)
                    tvSaved.text = " Saved :) "
                } else {
                    imgSaved.setImageResource(R.drawable.save1)
                    tvSaved.text =" Not saved :( "
                }


                tvHeader.text = currentItem.postHeader
                tvDisc.text = currentItem.postLink
                tvUpvotes.text = currentItem.postLikes
                tvComment.text = currentItem.postCommentsObj?.size.toString()
                var author = currentItem.postAuthor
                tvAuthor.text = "Suggested by $author"

                firebaseRef = FirebaseDatabase.getInstance().getReference("posts")



                //klikajem na post zagrużajem po id
                //peredajem cherez intent w PostDataActivity
                holder.itemView.setOnClickListener {

                    loadPostById(currentItem.postID) { post ->
                        if (post != null) {


                            val loadedPost = PostData(
                                postID = post.postID,
                                postHeader = post.postHeader,
                                postAuthor = post.postAuthor,
                                postLink = post.postLink,
                                postLikes = post.postLikes,
                                postDescription = post.postDescription,
                                postCommentsObj = post.postCommentsObj,
                                postComments = post.postComments,
                                postSaves = post.postSaves
                            )
                            val intent = Intent(it.context, PostDataActivity::class.java)

                            intent.putExtra("postID", loadedPost.postID)
                            intent.putExtra("postHeader", loadedPost.postHeader)
                            intent.putExtra("postAuthor", loadedPost.postAuthor)
                            intent.putExtra("postLink", loadedPost.postLink)
                            intent.putExtra("postLikes", loadedPost.postLikes)
                            intent.putExtra("postDescription", loadedPost.postDescription)
                            intent.putExtra("postComments", loadedPost.postComments)
                            intent.putExtra("postSaves", loadedPost.postSaves)


                            if (loadedPost.postCommentsObj != null) {
                                val comments = loadedPost.postCommentsObj as ArrayList<Comment>
                                intent.putParcelableArrayListExtra("postCommentsObj", comments)
                            }


                            it.context.startActivity(intent)

                        }

                    }




                }


//zagruzajem post po id . like+1/like-1,likedPost - paamiat' , zagruzajem post snowa
                imgUpvotes.setOnClickListener {
                    if (likedPosts.contains(postId)) {
                        // Remove like
                        likedPosts.remove(postId)
                        imgUpvotes.setImageResource(R.drawable.like1)
                        loadPostById(currentItem.postID) { post ->
                            if (post != null) {
                                val currentLikes = post.postLikes?.toIntOrNull() ?: 0
                                val updatedLikes = currentLikes - 1

                                val updatedPost = PostData(
                                    postID = currentItem.postID,
                                    postHeader = currentItem.postHeader,
                                    postAuthor = currentItem.postAuthor,
                                    postLink = currentItem.postLink,
                                    postLikes = updatedLikes.toString(),
                                    postDescription = currentItem.postDescription,
                                    postCommentsObj = currentItem.postCommentsObj,
                                    postComments = currentItem.postComments,
                                    postSaves = currentItem.postSaves
                                )

                                val updatedRef = firebaseRef.child(updatedPost.first())
                                updatedRef.setValue(updatedPost)
                                tvUpvotes.setText(updatedPost.postLikes)
                            }
                        }
                    } else {
                        // Add like
                        likedPosts.add(postId)
                        imgUpvotes.setImageResource(R.drawable.like2)
                        loadPostById(currentItem.postID) { post ->
                            if (post != null) {
                                val currentLikes = post.postLikes?.toIntOrNull() ?: 0
                                val updatedLikes = currentLikes + 1

                                val updatedPost = PostData(
                                    postID = currentItem.postID,
                                    postHeader = currentItem.postHeader,
                                    postAuthor = currentItem.postAuthor,
                                    postLink = currentItem.postLink,
                                    postLikes = updatedLikes.toString(),
                                    postDescription = currentItem.postDescription,
                                    postCommentsObj = currentItem.postCommentsObj,
                                    postComments = currentItem.postComments,
                                    postSaves = currentItem.postSaves
                                )

                                val updatedRef = firebaseRef.child(updatedPost.first())
                                updatedRef.setValue(updatedPost)
                                tvUpvotes.setText(updatedPost.postLikes)
                            }
                        }
                    }
                    saveLikedPosts()
                }


                imgSaved.setOnClickListener {
                    if (savedPosts.contains(postId)) {
                        // Remove like
                        savedPosts.remove(postId)
                        imgSaved.setImageResource(R.drawable.save1)
                        showToast(it.context, postId.toString(), "Removed Saved")
                        tvSaved.text = " Not saved :( "
                    } else {
                        // Add like
                        savedPosts.add(postId)
                        imgSaved.setImageResource(R.drawable.save2)
                        showToast(it.context, postId.toString(), "Saved")
                        tvSaved.text = " Saved :) "
                    }
                    saveSavedPosts()
                }
            }
        }
    }





    private fun loadPostById(postId: String?, onComplete: (PostData?) -> Unit) {
        firebaseRef = FirebaseDatabase.getInstance().getReference("posts")
        firebaseRef.orderByKey().equalTo(postId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val postSnapshot = snapshot.children.firstOrNull()
                    val post = postSnapshot?.getValue(PostData::class.java)
                    onComplete(post)
                } else {
                    onComplete(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch post: ${error.message}", Toast.LENGTH_SHORT).show()
                onComplete(null)
            }
        })
    }




    private fun showToast(context: Context, postId: String, buttonType: String) {
        val message = "Post ID: $postId - Button clicked: $buttonType"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    private fun saveLikedPosts() {
        val likedPostStrings = likedPosts.map { it.toString() }.toSet()
        preferences.edit().putStringSet("LIKED_POSTS", likedPostStrings).apply()
    }

    private fun loadLikedPosts() {
        val likedPostStrings = preferences.getStringSet("LIKED_POSTS", emptySet())
        likedPostStrings?.let { likedPosts.addAll(it.map { id -> id.toInt() }) }
    }

    private fun saveSavedPosts() {
        val savedPostStrings = savedPosts.map { it.toString() }.toSet()
        preferences1.edit().putStringSet("SAVED_POSTS", savedPostStrings).apply()
    }



    private fun loadSavedPosts() {
        val savedPostStrings = preferences1.getStringSet("SAVED_POSTS", emptySet())
        savedPostStrings?.let { savedPosts.addAll(it.map { id -> id.toInt() }) }
    }
}
