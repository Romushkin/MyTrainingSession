package com.example.mytrainingsession

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExercisesListActivity : AppCompatActivity() {

    val exercises = ExerciseDataBase.exercises

    private lateinit var toolbarMain: Toolbar
    private lateinit var listOfExercisesLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exercises_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listOfExercisesLV = findViewById(R.id.listOfExercisesLV)
        toolbarMain = findViewById(R.id.toolbarMain)
        toolbarMain.title = "Тренировки по фитнесу"
        setSupportActionBar(toolbarMain)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exercises)
        listOfExercisesLV.adapter = adapter

        listOfExercisesLV.onItemClickListener =
            AdapterView.OnItemClickListener {parent, view, position, id ->
                val intent = Intent(this, ExercisesActivity::class.java)
                intent.putExtra("exerciseIndex", position)
                startActivity(intent)
            }
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