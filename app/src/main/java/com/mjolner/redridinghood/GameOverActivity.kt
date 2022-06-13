package com.mjolner.redridinghood

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {
    private val APP_TOP_SCORE = "TopScore"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        var score = intent.getIntExtra("score",0)
        var scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = "Ways Found: $score"

        var prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (score > prefs.getInt(APP_TOP_SCORE, 0) ) {
            val editor = prefs.edit()
            editor.putInt(APP_TOP_SCORE, score).apply()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@GameOverActivity, MainActivity::class.java)
        startActivity(intent)
    }
    fun backHome(view: View){
        val intent = Intent(this@GameOverActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun replay(view: View){
        val intent = Intent(this@GameOverActivity, GameActivity::class.java)
        startActivity(intent)
        finish()
    }
}