package com.example.wordlegame

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var chancesLeft = 3
    private lateinit var guessEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var  submitButton: Button
    private lateinit var targetWordTextView: TextView
    private lateinit var chancesLeftTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        guessEditText = findViewById(R.id.guessEditText)
        resultTextView = findViewById(R.id.resultTextView)
        submitButton = findViewById(R.id.submitButton)
        targetWordTextView = findViewById(R.id.targetWordTextView)
        chancesLeftTextView = findViewById(R.id.chancesLeftTextView)



        submitButton.setOnClickListener {
            val guess = guessEditText.text.toString().toUpperCase()
            val result = checkGuess(guess)
            chancesLeft--

            if(result == "OOOO") {
                submitButton.isEnabled = false
                targetWordTextView.text = wordToGuess
                resultTextView.text = result
            }
            if (chancesLeft > 0) {
                resultTextView.text = result
                chancesLeftTextView.text = "Chances Left: " + chancesLeft
            }
            else if (chancesLeft == 0){
                targetWordTextView.text = wordToGuess
                submitButton.text = "Disabled"
                chancesLeftTextView.text = "Chances Left: " + chancesLeft
            }
        }
    }



    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Body,Book,Call,Card,Care,Case"
        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}