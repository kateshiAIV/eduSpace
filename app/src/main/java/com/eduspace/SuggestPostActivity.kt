package com.eduspace

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eduspace.databinding.ActivityLoginBinding
import com.eduspace.databinding.ActivitySuggestPostBinding
import com.eduspace.models.PostData
import com.eduspace.models.SuggestPost
import com.eduspace.models.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SuggestPostActivity : AppCompatActivity() {
    private lateinit var sharedPreferencesTest: SharedPreferences

    private lateinit var binding: ActivitySuggestPostBinding
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("suggested")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestPostBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.btnSubmit.setOnClickListener {
            sharedPreferencesTest = this.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val currentUser = sharedPreferencesTest.getString("currentUser", null)
            val postHeader = binding.etHeader.text.toString().trim()
            val postDesc = binding.etMessage.text.toString().trim()
            val postLink = binding.etLink.text.toString().trim()


            val isAnonymous = binding.sAnonymous.isChecked

            if(setError()) {
                return@setOnClickListener
            }


            val newPostID = generatePostID()
            var suggestPost = SuggestPost(newPostID,"Anonim", postDesc, postHeader, postLink )
            if(isAnonymous){
                suggestPost = SuggestPost(newPostID,"Anonim", postDesc, postHeader, postLink )
            }else{
                suggestPost = SuggestPost(newPostID,currentUser, postDesc, postHeader, postLink )
            }



            val userRef = myRef.child(newPostID)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        userRef.setValue(suggestPost)
                    } else {

                        userRef.setValue(suggestPost)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.")
                }
            })
            Toast.makeText(this, "  btnSubmit $currentUser", Toast.LENGTH_SHORT).show()

        }



    }
    private fun generatePostID(): String {
        val dateFormat = SimpleDateFormat("MMddmmss", Locale.getDefault())
        return dateFormat.format(Date())
    }


    private fun setError(): Boolean {
        var error : Boolean = false
        val postLink = binding.etLink.text.toString().trim()
        val postInfo = binding.etMessage.text.toString().trim()

        if (postLink.isEmpty()) {
            binding.etLink.error = "Link field cannot be empty."
            error = true
        } else {
            binding.etLink.error = null
        }

        return error
    }
}