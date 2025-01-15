package com.eduspace.fragments

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.eduspace.LeaderBoard
import com.eduspace.R
import com.eduspace.SuggestPostActivity
import com.eduspace.models.QuizData
import com.eduspace.models.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class quiz : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var currentStreak: TextView
    private lateinit var longestStreak: TextView
    private lateinit var trueImg: ImageButton
    private lateinit var falseImg: ImageButton
    private lateinit var leadersImg: ImageButton
    private lateinit var crossAnim: LottieAnimationView
    private lateinit var countdownBar: ProgressBar
    private lateinit var firebaseRef: DatabaseReference
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("usersRecords")



    private val questions = listOf(
        Pair("The Earth is the third planet from the Sun.", true),
        Pair("Jupiter is smaller than the Earth.", false),
        Pair("Saturn's rings are made of ice and rock.", true),
        Pair("A day on Venus is longer than a year on Venus.", true),
        Pair("The Moon is a planet.", false)
    )

    private var currentQuestionIndex = 0
    private var streak = 0
    private var longeststreak = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesTest: SharedPreferences

    private lateinit var countdownTimer: CountDownTimer
    private val countdownTime: Long  = 5000 // 6 seconds
    //val currentUser = sharedPreferencesTest.getString("currentUser", null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        questionText = view.findViewById(R.id.questionText)
        trueImg = view.findViewById(R.id.trueImg)
        falseImg = view.findViewById(R.id.falseImg)
        leadersImg = view.findViewById(R.id.leadersImg)
        currentStreak = view.findViewById(R.id.currentStreak)
        longestStreak = view.findViewById(R.id.longestStreak)
        crossAnim = view.findViewById(R.id.cross)
        countdownBar = view.findViewById(R.id.countdownBar)




        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val currentUser = sharedPreferences.getString("currentUser", "Unknown User")

//        Toast.makeText(requireContext(), "Hello, $currentUser!", Toast.LENGTH_SHORT).show()
        loadStreak(currentUser) { streak ->
            longeststreak = streak
            longestStreak.text = "Longest streak: $longeststreak"
        }







        // Add animation listener
        crossAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                resetCountdown()
                startCountdown()
                crossAnim.visibility = View.GONE
                countdownBar.visibility = View.VISIBLE
                currentStreak.visibility = View.VISIBLE
                longestStreak.visibility = View.VISIBLE
                questionText.visibility = View.VISIBLE
                trueImg.visibility = View.VISIBLE
                falseImg.visibility = View.VISIBLE
                leadersImg.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        loadQuestion()

        trueImg.setOnClickListener { checkAnswer(true) }
        leadersImg.setOnClickListener {
            val intent = Intent(requireContext(), LeaderBoard::class.java)
            startActivity(intent)
        }

//        sharedPreferencesTest = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
//        val storedUser = sharedPreferencesTest.getString("currentUser", null)
//
//        if (storedUser != null) {
//            Log.d(TAG, "Stored User: $storedUser")
//        } else {
//            Log.d(TAG, "No user is currently logged in.")
//        }

        falseImg.setOnClickListener { checkAnswer(false) }

        return view
    }




    private fun loadQuestion() {
        resetCountdown()
        startCountdown()
        currentQuestionIndex = (0..4).random()
        questionText.text = questions[currentQuestionIndex].first
    }

    private fun checkAnswer(answer: Boolean) {


        val correctAnswer = questions[currentQuestionIndex].second



        countdownBar.visibility = View.GONE
        currentStreak.visibility = View.GONE
        longestStreak.visibility = View.GONE
        questionText.visibility = View.GONE
        trueImg.visibility = View.GONE
        falseImg.visibility = View.GONE

        if (answer == correctAnswer) {
            streak++

            // success animation
            crossAnim.visibility = View.VISIBLE
            crossAnim.setAnimation(R.raw.success_animation)
            crossAnim.playAnimation()

            //  the longest streak
            if (streak > longeststreak) {
                longeststreak = streak
                saveLongestStreak()
            }

            currentStreak.text = "Current streak: $streak"
            longestStreak.text = "Longest streak: $longeststreak"

            loadQuestion()

        } else {
            // failure animation
            crossAnim.visibility = View.VISIBLE
            crossAnim.setAnimation(R.raw.failure_animation)
            crossAnim.playAnimation()

            resetQuiz()

            currentStreak.text = "Current streak: $streak"
            longestStreak.text = "Longest streak: $longeststreak"
        }
    }

    private fun startCountdown() {
        val maxProgress = 100
        countdownBar.max = maxProgress

        // Cancel prev animations
        if (::countdownTimer.isInitialized) {
            countdownTimer.cancel()
        }

        val animator = ObjectAnimator.ofInt(countdownBar, "progress", maxProgress, 0).apply {
            duration = countdownTime
            interpolator = LinearInterpolator()
        }

        animator.start()


        countdownTimer = object : CountDownTimer(countdownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                countdownBar.progress = 0
                checkAnswer(!questions[currentQuestionIndex].second)
            }
        }
        countdownTimer.start()
    }



    private fun resetCountdown() {
        if (::countdownTimer.isInitialized) {
            countdownTimer.cancel()
        }
        countdownBar.progress = 100
    }

    private fun saveLongestStreak() {
//        with(sharedPreferences.edit()) {
//            putInt("longest_streak", longeststreak)
//            apply()
//        }
        sharedPreferencesTest = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val currentUser = sharedPreferencesTest.getString("currentUser", null)

        if (currentUser != null) {
            val userRef = myRef.child(currentUser)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val currentStreak = snapshot.getValue(QuizData::class.java)?.userRecord ?: 0
                        val newStreak = if (longeststreak > currentStreak) longeststreak else currentStreak
                        userRef.setValue(QuizData(currentUser, newStreak))
                    } else {

                        userRef.setValue(QuizData(currentUser, longeststreak))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.")
                }
            })
        }
    }


//    private fun saveLongestStreak() {
////        with(sharedPreferences.edit()) {
////            putInt("longest_streak", longeststreak)
////            apply()
////        }
//        sharedPreferencesTest = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
//        val currentUser = sharedPreferencesTest.getString("currentUser", null)
//
//        val userData = QuizData(currentUser, longeststreak)
//        myRef.push().setValue(userData)
//
////        sharedPreferencesTest = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
////        val storedUser = sharedPreferencesTest.getString("currentUser", null)
////
////        if (storedUser != null) {
////            Log.d(TAG, "Stored User: $storedUser")
////        } else {
////            Log.d(TAG, "No user is currently logged in.")
////        }
//    }

    private fun resetQuiz() {
        currentQuestionIndex = 0
        streak = 0
        loadQuestion()
    }

    private fun endQuiz() {
        Toast.makeText(context, "Quiz Completed! Final Streak: $streak", Toast.LENGTH_LONG).show()
        resetQuiz()
    }



    private fun loadStreak(currentUser: String?, onComplete: (Int) -> Unit) {
        myRef.orderByChild("userName").equalTo(currentUser).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Toast.makeText(requireContext(), "Hello, $currentUser!", Toast.LENGTH_SHORT).show()
                var maxStreak = 0
                for (childSnapshot in snapshot.children) {
                    val quizData = childSnapshot.getValue(QuizData::class.java)
                    if (quizData != null) {
                        maxStreak = quizData.userRecord?: 0
                    }
                }
                onComplete(maxStreak)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.")
                onComplete(0)
            }
        })
    }

}
