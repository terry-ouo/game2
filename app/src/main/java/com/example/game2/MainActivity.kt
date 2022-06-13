package com.example.game2

import android.graphics.Canvas
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.game2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.game2.BallSurfaceView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var gameStatus = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.text = "no touch"


    }

    fun gameStatus(p0: View?) {
        lateinit var ball: BallSurfaceView
        gameStatus = !gameStatus
        GlobalScope.launch(Dispatchers.Main) {
            while (gameStatus) {
                delay(25)
                val canvas: Canvas = binding.ball.holder.lockCanvas()
                binding.ball.drawSomething(canvas)
                binding.ball.holder.unlockCanvasAndPost(canvas)
//                react()
//                var ballX = ball.ballOrigX.toString()
//                if (ball.ballOrigX>400){
//                    binding.t1.text = "ball >400"
//                }else{
//                    binding.t1.text = "ball <400"
//                }

//                binding.t1.text = ball.ballMoveX.toString()

            }
        }
    }

}