package com.example.game2

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.game2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnTouchListener,
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    lateinit var binding: ActivityMainBinding
    private var gameStatus = false
    private var gameEnd = false
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
        gameStatus = !gameStatus
        GlobalScope.launch(Dispatchers.Main) {
            while (gameStatus) {
                if (gameEnd){
                    binding.ball.ballSpeed_min = 8
                    binding.ball.ballSpeed_min = 15
                    binding.ball.ballOrigX = 400
                    binding.ball.ballOrigY = 1000
                    binding.ball.ballMoveX = 0
                    binding.ball.ballMoveY = 8
                    gameEnd = !gameEnd
                }
                delay(25)
                val canvas: Canvas = binding.ball.holder.lockCanvas()
                binding.ball.drawSomething(canvas)
                binding.ball.holder.unlockCanvasAndPost(canvas)
                //板子回彈
                if ((binding.ball.ballOrigX > paddlePosition && binding.ball.ballOrigX < paddlePosition + 210) && (binding.ball.ballOrigY in 1100..1200)) {
                    binding.ball.ballMoveY =
                        (binding.ball.ballSpeed_min..binding.ball.ballSpeed_max).random() * (-1)
                }
                if (binding.ball.ballOrigY > 1300) {
                    gameStatus = !gameStatus
                    gameEnd = true
                }
            }
            if (gameEnd){
                binding.button.text = "重新開始"
            }else{
                binding.button.text = "繼續"
            }


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

    fun changeView(view: View) {
        gameStatus = false
        startActivity(Intent(this, CheckMenu::class.java))
    }
}