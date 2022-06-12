package com.example.game2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class BallSurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),SurfaceHolder.Callback2 {
    var surfaceHolder: SurfaceHolder = holder
    var Ball: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ball)
    private var ballOrigX : Int = 0
    private var ballOrigY : Int = 0
    private var ballMoveX : Int = 4
    private var ballMoveY : Int = 4


    init {
        surfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        val canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)

    }

    fun drawSomething(canvas: Canvas) {
        ballOrigX += ballMoveX
        ballOrigY += ballMoveY
//        canvas.drawBitmap(Ball, 500f, 500f, null)
        if (ballOrigX>width-Ball.width || ballOrigX<= 0){
            ballMoveX *= -1
        }
        if (ballOrigY>height-Ball.height || ballOrigY<= 0){
            ballMoveY *= -1
        }
        canvas.drawBitmap(Ball, ballOrigX.toFloat(), ballOrigY.toFloat(), null)

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

    }
}