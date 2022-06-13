package com.example.game2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.game2.databinding.ActivityMainBinding


class BallSurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback2 {
    lateinit var binding: ActivityMainBinding
    var myPaint = Paint()
    var surfaceHolder: SurfaceHolder = holder
    var Ball: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ball)
    var Brick: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.green4)
    var Brick2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.orange4)
    var ballOrigX: Int = 400
    var ballOrigY: Int = 1000
    var ballMoveX: Int = 8
    var ballMoveY: Int = 8


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
        var str: String = "123"
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        ballOrigX += ballMoveX
        ballOrigY += ballMoveY
        if (ballOrigX > 400) {
            str = "123"
        } else {
            str = "345"
        }
//        canvas.drawBitmap(Ball, 500f, 500f, null)
        detectX()
        detectY()
//        detectBrick()
        canvas.drawRGB(255, 228, 225)
        canvas.drawBitmap(Ball, ballOrigX.toFloat(), ballOrigY.toFloat(), null)
        canvas.drawBitmap(Brick, 20f, 0f, null)
        canvas.drawBitmap(Brick2, 340f, 0f, null)
        canvas.drawText("$ballOrigX  $ballOrigY", 500f, 1000f, myPaint)

    }

    fun detectX() {
        if (ballOrigX > width - Ball.width || ballOrigX <= 0) {
            ballMoveX *= -1
            ballMoveY = if (ballMoveY > 0) {
                (7..12).random()

            } else {
                (7..12).random() * (-1)
            }

        }
    }

    fun detectY() {
        if (ballOrigY > height - Ball.height || ballOrigY <= 0) {
            ballMoveY *= -1
            ballMoveX = if (ballMoveX > 0) {
                (7..12).random()
            } else {
                (7..12).random() * (-1)
            }

        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

    }

    fun detectBallTouchBrick() {
        if (ballOrigX) {

        }
        if (ballOrigX){

        }
    }

}
