package com.mjolner.redridinghood

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var valueOfScoreTextView: TextView? = null
    private val APP_TOP_SCORE = "score"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        valueOfScoreTextView = findViewById(R.id.valueOfScoreTextView)
        valueOfScoreTextView?.text =  getSharedPreferences("settings", Context.MODE_PRIVATE).getInt(APP_TOP_SCORE, 0).toString()
    }

    fun startGame(view: View){
        var intent = Intent(this@MainActivity,GameActivity::class.java)
        startActivity(intent)
    }
}