package com.example.game2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.game2.databinding.ActivityMainBinding
import kotlinx.coroutines.delay


class BallSurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback2 {
    lateinit var binding: ActivityMainBinding
    var myPaint = Paint()
    var surfaceHolder: SurfaceHolder = holder
    var ball: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ball)
    var background: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg)
    private var brick: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.green4)
    private var brick2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.orange4)
    var ballOrigX: Int = 400
    var ballOrigY: Int = 1000
    var ballMoveX: Int = 0
    var ballMoveY = 8
    var ballSpeed_max = 15
    var ballSpeed_min = 8


    init {
        surfaceHolder.addCallback(this)
        myPaint.color = Color.BLACK
        myPaint.textSize = 20F
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        val canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)

    }

    fun drawSomething(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.drawBitmap(background, 0f, 0f, null)
        //canvas.drawRGB(255, 228, 225)
//        canvas.drawBitmap(ball, ballOrigX.toFloat(), ballOrigY.toFloat(), null)
        canvas.drawBitmap(brick, 20f, 0f, null)
        canvas.drawBitmap(brick2, 340f, 0f, null)
//       canvas.drawText("$ballOrigX  $ballOrigY", 500f, 1000f, myPaint)
        ball(canvas)

    }


    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

    }

    //偵測是否超出邊框的範圍
    private fun detectEdge() {
        //左右
        if (ballOrigX > width - ball.width || ballOrigX <= 0) {
            ballMoveX *= -1
            ballMoveY = if (ballMoveY > 0) {
                (ballSpeed_min..ballSpeed_max).random()

            } else {
                (ballSpeed_min..ballSpeed_max).random() * (-1)
            }

        }
        //上下
        if (ballOrigY > height - ball.height || ballOrigY <= 0) {
            ballMoveY *= -1
            ballMoveX = if (ballMoveX > 0) {
                (ballSpeed_min..ballSpeed_max).random()
            } else {
                (ballSpeed_min..ballSpeed_max).random() * (-1)
            }

        }
    }


    private fun ball(canvas: Canvas) {
        canvas.drawBitmap(ball, ballOrigX.toFloat(), ballOrigY.toFloat(), null)
        ballOrigX += ballMoveX
        ballOrigY += ballMoveY
        detectEdge()
//        canvas.drawText("$ballOrigX  $ballOrigY", 500f, 1000f, myPaint)
    }


}
