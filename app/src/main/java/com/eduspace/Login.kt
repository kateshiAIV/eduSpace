package com.eduspace

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eduspace.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener{

            if(setError()) {
                return@setOnClickListener
            }




            auth.signInWithEmailAndPassword(binding.email.getText().toString().trim(), binding.password.getText().toString().trim())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        fetchUsername(binding.email.text.toString().trim()) { userName ->
                            if (userName != null) {
                                val currentUser = userName
                                Log.d(TAG, "Current User: $currentUser")
                                val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("currentUser", currentUser)
                                editor.apply()
                            } else {
                                Log.d(TAG, "Username not found for email: ${binding.email.text}")
                            }
                        }

                        fetchUserRole(binding.email.text.toString().trim()) { userRole ->
                            if (userRole != null) {
                                val currentUserRole = userRole
                                Log.d(TAG, "Current User: $currentUserRole")
                                val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("currentUserRole", currentUserRole)
                                editor.apply()
                            } else {
                                Log.d(TAG, "Username not found for email: ${binding.email.text}")
                            }
                        }


                    } else {
                        binding.password.error = "Check your password"
                    }
                }

        }
        binding.mtRegistration.setOnClickListener{
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }


    }



    private fun setError(): Boolean {



        var error : Boolean = false
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        checkEmail(email){isUnique ->
            if (isUnique){             binding.email.error = "Email is not registered." // Clear any previous error

            }
            error = isUnique
        }

        if (email.isEmpty()) {
            binding.email.error = "Email field cannot be empty."
            error = true
        } else {
            binding.email.error = null
        }


        if (password.isEmpty()) {
            binding.password.error = "Password field cannot be empty."
            error = true
        } else {
            binding.password.error = null
        }




        return error

    }



    fun fetchUsername(newUserEmail: String, callback: (String?) -> Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userName: String? = null
                snapshot.children.forEach { child ->
                    val existingUserEmail = child.child("userEmail").getValue(String::class.java)
                    if (existingUserEmail == newUserEmail) {
                        userName = child.child("userName").getValue(String::class.java)
                        Log.d(TAG, "Username found: $userName")
                        return@forEach
                    }
                }
                callback(userName)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching username: ${error.message}")
                binding.email.error = "Unknown error."
                callback(null)
            }
        })
    }



    fun fetchUserRole(newUserEmail: String, callback: (String?) -> Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userRole: String? = null
                snapshot.children.forEach { child ->
                    val existingUserEmail = child.child("userEmail").getValue(String::class.java)
                    if (existingUserEmail == newUserEmail) {
                        userRole = child.child("userRole").getValue(String::class.java)
                        Log.d(TAG, "Username found: $userRole")
                        return@forEach
                    }
                }
                callback(userRole)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching username: ${error.message}")
                binding.email.error = "Unknown error."
                callback(null)
            }
        })
    }






    fun checkEmail(newUsername: String, callback: (Boolean) -> Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var isUnique = true
                snapshot.children.forEach { child ->
                    val existingUsername = child.child("userEmail").getValue(String::class.java)
                    if (existingUsername == newUsername) {
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


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}