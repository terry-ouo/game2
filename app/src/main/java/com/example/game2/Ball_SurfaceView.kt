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

    private var brick1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.green4)
    private var brick2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.orange4)
    private var brick3: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.blue4)
    private var brick4: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.yellow4)
    private var brick5: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.red4)
    private var brick6: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.blue4)

    var ballOrigX: Int = 400
    var ballOrigY: Int = 1000
    var ballMoveX: Int = 0
    var ballMoveY = 8
    var ballSpeed_max = 15
    var ballSpeed_min = 8

    var detectBrick1: Boolean = true
    var detectBrick2: Boolean = true
    var detectBrick3: Boolean = true
    var detectBrick4: Boolean = true
    var detectBrick5: Boolean = true
    var detectBrick6: Boolean = true
    var countBrick: Int = 6


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
        //canvas.drawBitmap(ball, ballOrigX.toFloat(), ballOrigY.toFloat(), null)
        createBreakBrick(canvas)

        canvas.drawText("$countBrick", 500f, 1000f, myPaint)
        //canvas.drawText("$ballOrigX  $ballOrigY", 500f, 1000f, myPaint)
        ball(canvas)

    }


    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

    }

    //?????????????????????????????????
    private fun detectEdge() {
        //??????
        if (ballOrigX > width - ball.width ) {
            ballMoveX += (-1) * (10..20).random()
            ballMoveY = if (ballMoveY > 0) {
                (ballSpeed_min..ballSpeed_max).random()

            } else {
                (ballSpeed_min..ballSpeed_max).random() * (-1)
            }

        }
        if(ballOrigX <= 0){
            ballMoveX += (1) * (10..20).random()
            ballMoveY = if (ballMoveY > 0) {
                (ballSpeed_min..ballSpeed_max).random()

            } else {
                (ballSpeed_min..ballSpeed_max).random() * (-1)
            }
        }
        //??????
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
        //canvas.drawText("$ballOrigX  $ballOrigY", 500f, 1000f, myPaint)
    }


    fun createBreakBrick(canvas: Canvas) {
        //first line green block
        if (detectBrick1) {
            canvas.drawBitmap(brick1, 90f, 0f, null)
            if(detectBrick(40, 350, 0, 60)){
                detectBrick1 = false
            }
        }
        //first line orange block
        if (detectBrick2) {
            canvas.drawBitmap(brick2, 330f, 0f, null)
            if(detectBrick(280, 590, 0, 60)){
                detectBrick2 = false
            }
        }
        //first line blue block
        if (detectBrick3) {
            canvas.drawBitmap(brick3, 570f, 0f, null)
            if(detectBrick(520, 760, 0, 60)){
                detectBrick3 = false
            }
        }
        //second line yellow block
        if (detectBrick4) {
            canvas.drawBitmap(brick4, 210f, 60f, null)
            if(detectBrick(160, 460, 60, 120)){
                detectBrick4 = false
            }
        }
        //second line red block
        if (detectBrick5) {
            canvas.drawBitmap(brick5, 450f, 60f, null)
            if(detectBrick(400, 700, 60, 120)){
                detectBrick5 = false
            }
        }
        //thrid line blue block
        if (detectBrick6) {
            canvas.drawBitmap(brick6, 330f, 120f, null)
            if(detectBrick(280, 590, 120, 180)){
                detectBrick6 = false
            }
        }
    }

    fun detectBrick(pos1: Int, pos2: Int, pos3: Int, pos4: Int): Boolean {
        var status: Boolean = true
        if ((ballOrigX >= pos1 && ballOrigX <= pos2) && (ballOrigY >= pos3 && ballOrigY <= pos4)) {
            ballMoveY = (ballSpeed_min..ballSpeed_max).random()
            ballMoveX = (ballSpeed_min..ballSpeed_max).random()
            countBrick--
            return status
        }else{
            status = false
            return status
        }
    }
}


