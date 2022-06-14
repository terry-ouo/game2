package com.example.game2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.game2.databinding.ActivityCheckMenuBinding
import com.example.game2.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CheckMenu : AppCompatActivity() {
    lateinit var binding: ActivityCheckMenuBinding
    var db = FirebaseFirestore.getInstance()
    var user: MutableMap<String, Any> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnrank.setOnClickListener {
            db.collection("Users")
                .orderBy("時間", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var msg: String = "排行榜\n\n"
                        var rank: Int=1
                        for (document in task.result!!) {
                            msg += rank.toString()+". "+document.data["使用者名稱"] + " " + document.data["時間"].toString()+"秒\n\n"
                            rank++
                        }
                        if (msg != "") {
                            binding.txv.text = msg
                        } else {
                            binding.txv.text = "虛以待位"
                        }
                    }
                }
        }

    }
    fun changeView(view : View){
        startActivity(Intent(this, MainActivity::class.java))
    }
}