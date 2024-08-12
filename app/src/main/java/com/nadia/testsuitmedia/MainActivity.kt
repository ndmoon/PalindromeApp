package com.nadia.testsuitmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputName: EditText = findViewById(R.id.editName)
        val inputPalindrome: EditText = findViewById(R.id.editPalindrome)
        val checkBtn: Button = findViewById(R.id.btnCheck)
        val nextBtn: Button = findViewById(R.id.btnNext)

        // Button Check
        checkBtn.setOnClickListener {
            val text = inputPalindrome.text.toString().replace("\\s".toRegex(),"")
            val isPalindrome = text.equals(text.reversed(), ignoreCase = true)
            val message = if (isPalindrome) "isPalindrome" else "not Palindrome"
            showDialog(message)
        }

        // Button Next
        nextBtn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("USERNAME", inputName.text.toString())
            startActivity(intent)
        }
        
    }

    // message
    private fun showDialog(message: String) {
        AlertDialog.Builder(this).setMessage(message).setPositiveButton("OK",null).show()
    }
}