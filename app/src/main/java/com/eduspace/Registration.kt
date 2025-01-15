package com.eduspace

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.eduspace.databinding.ActivityRegistrationBinding
import com.eduspace.models.QuizData
import com.eduspace.models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseRef: DatabaseReference
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")
    val myRefRec = database.getReference("usersRecords")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.btnRegister.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val password1 = binding.password1.text.toString().trim()

            if(setError()) {
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        val currentDate = LocalDateTime.now()
                        val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))

                        val userData = UserData(username, email, "user", formattedDate)


                        myRef.push().setValue(userData)



                        val currentUser = username // Set currentUser
                        val currentUserRole = "user"
                        Log.d(TAG, "Current User: $currentUser")
                        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("currentUser", currentUser)
                        editor.putString("currentUserRole", "user")
                        editor.apply() // Commit the changes








                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        binding.email.error = "Registration failed: ${task.exception?.message}"
                    }
                }



        }


//        binding.btnRegister.setOnClickListener {
//
//            val username = binding.username.text.toString()
//            val email = binding.email.text.toString()
//
//            checkUsername(username){isUnique ->
//                if (isUnique) {
//
//                    if (binding.password.getText().toString().trim() == binding.password1.getText()
//                            .toString().trim()
//                    ) {
//
//
//                        auth.createUserWithEmailAndPassword(
//                            binding.email.getText().toString().trim(),
//                            binding.password.getText().toString().trim()
//                        )
//                            .addOnCompleteListener(this) { task ->
//                                if (task.isSuccessful) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success")
//                                    val user = auth.currentUser
//                                    val intent = Intent(this, MainActivity::class.java)
//                                    startActivity(intent)
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                                    Toast.makeText(
//                                        baseContext,
//                                        "Registration failed.",
//                                        Toast.LENGTH_SHORT,
//                                    ).show()
//                                }
//                            }
//                    }else{
//                        Toast.makeText(
//                            baseContext,
//                            "Passwords don't match.",
//                            Toast.LENGTH_SHORT,
//                        ).show()
//                    }
//
//                    val user = UserData(username, email)
//                    myRef.push().setValue(user)
//
//                }else{
//                    binding.usernameLayout.error = "Username is already taken. Please choose another."
//                }
//
//            }
//
//        }
        binding.mtLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }

    private fun setError(): Boolean {
        var error : Boolean = false
        val username = binding.username.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val password1 = binding.password1.text.toString().trim()

        if (username.isEmpty()) {
            binding.username.error = "Username field cannot be empty."
            error = true
        } else {
            binding.username.error = null
        }
        if (email.isEmpty()) {
            binding.email.error = "Email field cannot be empty."
            error = true
        } else {
            binding.email.error = null
        }

        if (password.length < 6) {
            binding.password.error = "Password must be at least 6 characters long."
            error = true
        } else {
            binding.password.error = null
        }
        checkUsername(username) { isUnique ->
                error = !isUnique
        }
        checkEmail(email) { isUnique ->
            error = !isUnique
        }
        if (password != password1){
            binding.password1.error = "Passwords don't match."
            error = true
        } else {
            binding.password1.error = null
        }

        return error
    }






    fun checkEmail(newUsername: String, callback: (Boolean) -> Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var isUnique = true
                snapshot.children.forEach { child ->
                    val existingUsername = child.child("userEmail").getValue(String::class.java)
                    if (existingUsername == newUsername) {
                        binding.email.error = "Email is already registered."
                        isUnique = false
                        return
                    }
                }
                callback(isUnique)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }


    fun checkUsername(newUsername: String, callback: (Boolean) -> Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var isUnique = true
                snapshot.children.forEach { child ->
                    val existingUsername = child.child("userName").getValue(String::class.java)
                    if (existingUsername == newUsername) {
                        binding.username.error = "Username is already taken. Please choose another."
                        isUnique = false
                        return
                    }
                }
                callback(isUnique)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(false)
            }
        })
    }


    public override fun onStart(){
        super.onStart()
        val currentUser = auth.currentUser

        if(currentUser!= null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}