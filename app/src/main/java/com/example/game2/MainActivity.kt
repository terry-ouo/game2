package com.example.game2

import android.graphics.Canvas
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.game2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.activity_main.*
import com.example.game2.BallSurfaceView

class MainActivity : AppCompatActivity(), View.OnTouchListener,
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    lateinit var binding: ActivityMainBinding
    private var gameStatus = false
    var paddlePosition: Int = 0
    lateinit var gDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.text = "開始"
        binding.ball.ballSpeed_min = 8
        binding.ball.ballSpeed_min = 15
        binding.img2.setOnTouchListener(this)
        gDetector = GestureDetector(this, this)


    }

    fun gameStatus(p0: View?) {
        binding.button.text = "暫停"
        lateinit var ball: BallSurfaceView
        gameStatus = !gameStatus
        GlobalScope.launch(Dispatchers.Main) {
            while (gameStatus) {
                delay(25)
                val canvas: Canvas = binding.ball.holder.lockCanvas()
                binding.ball.drawSomething(canvas)
                binding.ball.holder.unlockCanvasAndPost(canvas)
                binding.t1.text = binding.ball.ballOrigY.toString()
                if (binding.ball.ballOrigY >= 1330) {
                    gameStatus = false
                    binding.ball.ballOrigY = 1000
                    binding.ball.ballOrigX = 400
                    binding.ball.ballMoveX = 0
                    binding.ball.ballMoveY = 8
                }
                if (binding.ball.ballOrigX > paddlePosition && binding.ball.ballOrigX < paddlePosition + 210 && binding.ball.ballOrigY > 1200 && binding.ball.ballOrigY < 1150) {
                    binding.ball.ballMoveY =
                        (binding.ball.ballSpeed_min..binding.ball.ballSpeed_max).random() * (-1)
                }
            }
            binding.button.text = "開始"
            binding.ball.ballSpeed_min = 8
            binding.ball.ballSpeed_min = 15
        }

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_MOVE) {
            v?.x = event.rawX - v!!.width / 2
            paddlePosition = v.x.toInt()
        }
        gDetector.onTouchEvent(event)

        return true
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
        binding.ball.ballSpeed_min += 5
        binding.ball.ballSpeed_max += 5
        return true
    }

    override fun onDoubleTap(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTapEvent(p0: MotionEvent?): Boolean {
        return true
    }
}