package com.example.game2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CheckMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_menu)
    }
    fun changeView(view : View){
        startActivity(Intent(this, MainActivity::class.java))
    }
}