package com.nadia.testsuitmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val txtName:TextView = findViewById(R.id.txtName)
        val txtSelected: TextView = findViewById(R.id.txtSelected)
        val chooseBtn: Button = findViewById(R.id.btnChoose)

        val name = intent.getStringExtra("USERNAME")
        txtName.text = "Hello, $name"

        val selectedUserName = intent.getStringExtra("SELECTED_USER_NAME")
        txtSelected.text = selectedUserName ?: "Selected User Name"

        chooseBtn.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}