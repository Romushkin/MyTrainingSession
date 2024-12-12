package com.example.mytrainingsession

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExercisesActivity : AppCompatActivity() {

    val exercise = ExerciseDataBase.exercises

    private lateinit var toolbarMain: Toolbar
    private lateinit var trainingTV: TextView
    private lateinit var startBTN: Button
    private lateinit var exerciseTitleTV: TextView
    private lateinit var exerciseDescriptionTV: TextView
    private lateinit var timerTV: TextView
    private lateinit var nextExerciseBTN: Button
    private lateinit var exerciseImageIV: ImageView

    private var exerciseIndex = 0
    private lateinit var currentExercise: Exercise
    private lateinit var timer: CountDownTimer

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exercises)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        toolbarMain.title = "Тренировки по фитнесу"
        setSupportActionBar(toolbarMain)

        trainingTV = findViewById(R.id.trainingTV)
        startBTN = findViewById(R.id.startBTN)
        exerciseTitleTV = findViewById(R.id.exerciseTitleTV)
        exerciseDescriptionTV = findViewById(R.id.exerciseDescriptionTV)
        timerTV = findViewById(R.id.timerTV)
        nextExerciseBTN = findViewById(R.id.nextExerciseBTN)
        exerciseImageIV = findViewById(R.id.exerciseImageIV)

        exerciseIndex = intent.getIntExtra("exerciseIndex", 0)


        startBTN.setOnClickListener {
            startWorkout()
        }

        nextExerciseBTN.setOnClickListener {
            completeExercise()
        }

    }

    private fun completeExercise() {
        timer.cancel()
        nextExerciseBTN.isEnabled = false
        startNextExercise()
    }

    private fun startWorkout() {
        exerciseTitleTV.text = "Начало тренировки"
        startBTN.isEnabled = false
        startBTN.text = "Процесс тренировки"
        startNextExercise()
    }

    private fun startNextExercise() {
        if (exerciseIndex < exercise.size) {
            currentExercise = exercise[exerciseIndex]
            exerciseTitleTV.text = currentExercise.name
            exerciseDescriptionTV.text = currentExercise.description
            exerciseImageIV.setImageResource(exercise[exerciseIndex].gifImage)
            timerTV.text = formatTime(currentExercise.durationInSeconds)
            timer = object : CountDownTimer(
                currentExercise.durationInSeconds * 1000L,
                1000
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTV.text = formatTime((millisUntilFinished / 1000).toInt())
                }

                override fun onFinish() {
                    timerTV.text = "Упражнение завершено"
                    exerciseImageIV.visibility = View.VISIBLE
                    nextExerciseBTN.isEnabled = true
                    exerciseImageIV.setImageResource(0)
                }
            }.start()
            exerciseIndex++
        } else {
            exerciseTitleTV.text = "Тренировка завершена"
            exerciseDescriptionTV.text = ""
            timerTV.text = ""
            nextExerciseBTN.isEnabled = false
            startBTN.isEnabled = true
            startBTN.text = "Начать снова"
            exerciseIndex = 0
        }
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) finishAffinity()
        return super.onOptionsItemSelected(item)
    }
}
