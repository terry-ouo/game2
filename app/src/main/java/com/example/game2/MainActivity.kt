package com.example.game2

import android.graphics.Canvas
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.example.game2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.game2.BallSurfaceView

class MainActivity : AppCompatActivity(),View.OnTouchListener {
    lateinit var binding: ActivityMainBinding
    private var gameStatus = false
    var paddlePosition : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.text = "no touch"
        binding.img2.setOnTouchListener(this)

    }

    fun gameStatus(p0: View?) {
        binding.button.text = "restart"
        lateinit var ball: BallSurfaceView
        gameStatus = !gameStatus
        GlobalScope.launch(Dispatchers.Main) {
            while (gameStatus) {
                delay(25)
                val canvas: Canvas = binding.ball.holder.lockCanvas()
                binding.ball.drawSomething(canvas)
                binding.ball.holder.unlockCanvasAndPost(canvas)
                binding.t1.text = binding.ball.ballOrigY.toString()
                if (binding.ball.ballOrigY >= 1360){
                    gameStatus = false
                }
                if (binding.ball.ballOrigX>paddlePosition && binding.ball.ballOrigX< paddlePosition+210 && binding.ball.ballOrigY >1100){
                    binding.ball.ballMoveY = (8..14).random() * (-1)
                }

            }
            binding.ball.ballOrigY = 1000
            binding.ball.ballOrigX = 400
        }

    }
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_MOVE) {
            v?.x = event.rawX - v!!.width / 2
            binding.txv.text = v.x.toString()
            paddlePosition = v.x.toInt()
        }
        return true
    }
}