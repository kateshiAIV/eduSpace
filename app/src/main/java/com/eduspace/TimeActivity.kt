package com.eduspace

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

private lateinit var firebaseRef: DatabaseReference
private lateinit var sharedPreferencesTest: SharedPreferences

class TimeActivity : AppCompatActivity() {

    private lateinit var registrationDurationText: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var registrationTime: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseRef = FirebaseDatabase.getInstance().getReference("users")
        sharedPreferencesTest = this.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val currentUser = sharedPreferencesTest.getString("currentUser", null)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        registrationDurationText = findViewById(R.id.registrationDurationText)

        fetchDate(currentUser.toString().trim()) { date ->
            if (date != null) {
                registrationTime = convertDateToMillis(date)
                startUpdatingRegistrationDuration()
            } else {
                Toast.makeText(
                    this@TimeActivity,
                    "Error fetching registration date",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun convertDateToMillis(date: String): Long? {
        val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.getDefault())
        return sdf.parse(date)?.time
    }

    private fun startUpdatingRegistrationDuration() {
        handler.post(object : Runnable {
            override fun run() {
                registrationTime?.let {
                    val elapsedTime = calculateElapsedTime(it)
                    registrationDurationText.text = formatElapsedTime(elapsedTime)
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    private fun calculateElapsedTime(registrationTime: Long): Long {
        val currentTime = System.currentTimeMillis()
        return abs(currentTime - registrationTime)
    }

    private fun formatElapsedTime(elapsedTimeMillis: Long): String {
        val seconds = elapsedTimeMillis / 1000 % 60
        val minutes = elapsedTimeMillis / (1000 * 60) % 60
        val hours = elapsedTimeMillis / (1000 * 60 * 60) % 24
        val days = elapsedTimeMillis / (1000 * 60 * 60 * 24)
        return getString(R.string.registration_duration, days, hours, minutes, seconds)
    }

    private fun fetchDate(newUserEmail: String, callback: (String?) -> Unit) {
        firebaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userDate: String? = null
                snapshot.children.forEach { child ->
                    val existingUserEmail = child.child("userName").getValue(String::class.java)
                    if (existingUserEmail == newUserEmail) {
                        userDate = child.child("userDate").getValue(String::class.java)
                        return@forEach
                    }
                }
                callback(userDate)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TimeActivity", "Error fetching username: ${error.message}")
                callback(null)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Stop updates when the activity is destroyed
    }
}
