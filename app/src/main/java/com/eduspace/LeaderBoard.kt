package com.eduspace

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eduspace.adapter.RvLeaderBoardAdapter
import com.eduspace.models.QuizData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LeaderBoard : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var leaderBoardAdapter: RvLeaderBoardAdapter

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("usersRecords")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        recyclerView = findViewById(R.id.rvLeaders)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load data from Firebase and pass it to the adapter
        loadUserRecords { records ->
            leaderBoardAdapter = RvLeaderBoardAdapter(records)
            recyclerView.adapter = leaderBoardAdapter
        }
    }

    // Fetch user records from Firebase and pass the list to the callback
//    private fun loadUserRecords(onComplete: (List<QuizData>) -> Unit) {
//        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val records = mutableListOf<QuizData>()
//                for (recordSnapshot in snapshot.children) {
//                    val record = recordSnapshot.getValue(QuizData::class.java)
//                    if (record != null) {
//                        records.add(record)
//                    }
//                }
//                onComplete(records)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@LeaderBoard, "Failed to fetch records: ${error.message}", Toast.LENGTH_SHORT).show()
//                onComplete(emptyList())
//            }
//        })
//    }

    private fun loadUserRecords(onComplete: (List<QuizData>) -> Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records = mutableListOf<QuizData>()
                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(QuizData::class.java)
                    if (record != null) {
                        records.add(record)
                    }
                }


                val sortedRecords = records.sortedByDescending { it.userRecord }

                onComplete(sortedRecords)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LeaderBoard, "Failed to fetch records: ${error.message}", Toast.LENGTH_SHORT).show()
                onComplete(emptyList())
            }
        })
    }

}
