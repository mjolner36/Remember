package com.mjolner.redridinghood

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private var size = 1
    var sequenceOfNumbers:MutableList<Int> = mutableListOf()
    private var marker:Int = 0
    var score = 0
    var gameActive: Boolean = false
    var scoreText:TextView? =null
    var correctAnswer:MediaPlayer?= null
    var wrongAnswer:MediaPlayer?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        scoreText = findViewById(R.id.scoreTextView)
        correctAnswer = MediaPlayer.create(this,R.raw.correct_answer)
        wrongAnswer = MediaPlayer.create(this,R.raw.wrong_answer)
        randomSubsequence()
        showSequence()
        Log.d("game",sequenceOfNumbers.toString())
    }



    private fun randomSubsequence(){
        for (i in 0 until size){
            sequenceOfNumbers?.add(i,randomNumber())
        }
    }

    private fun randomNumber(): Int {
        val rand = Random(System.nanoTime())
        return (0..15).random(rand)
    }



    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@GameActivity,MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun clickOnItem(view: View){
        if (gameActive){
            Log.d("game","click on ${view.tag}")
            Log.d("game", sequenceOfNumbers[marker].toString())
            if (view.tag.toString().toInt() == sequenceOfNumbers[marker]){
                marker += 1
                Log.d("game","true")
                if (marker == sequenceOfNumbers.size){
                    correctAnswer!!.start()
                    newSequence()
                    score+=1
                    scoreText!!.text = score.toString()
                }
            }
            else {
                wrongAnswer!!.start()
                Log.d("game","false")
                gameOver()

            }
        }
    }


    fun showSequence(){
            lifecycleScope.launch {
                show()
            }
    }

    suspend fun show(){
        gameActive = false
        for (value in sequenceOfNumbers) {
            val viewImage = "imageView$value"
            var temp = resources.getIdentifier(viewImage, "id", packageName);
            var imageView = findViewById<ImageView>(temp)
            imageView.setImageResource(R.drawable.home_correct)
            delay(800)
            imageView.setImageResource(R.drawable.home_wrong)
        }
        gameActive = true
        Toast.makeText(this,"Repeat this",Toast.LENGTH_SHORT).show()
    }

    private fun newSequence(){
        size+=1
        marker = 0
        sequenceOfNumbers.clear()
        randomSubsequence()
        showSequence()
        Log.d("game",sequenceOfNumbers.toString())
    }

    private fun gameOver(){
        var intent = Intent(this@GameActivity,GameOverActivity::class.java)
        intent.putExtra("score",score)
        startActivity(intent)
        finishAffinity()
    }
}