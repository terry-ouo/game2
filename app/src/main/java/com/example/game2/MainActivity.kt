package com.example.game2

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.game2.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), View.OnTouchListener,
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    lateinit var binding: ActivityMainBinding
    private var gameStatus = false
    private var gameEnd = false
    var finalscore: Int = 0
    var paddlePosition: Int = 0
    var secondsCount: Int = 0    //計時
    var statecheck: Boolean = false
    var db = FirebaseFirestore.getInstance()
    var user: MutableMap<String, Any> = HashMap()
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
        binding.time.text = secondsCount.toString()


        //上傳使用者資料
        binding.btnUpdate.setOnClickListener {
            if (binding.user.text.toString() == "") {
                Toast.makeText(this, "請輸入挑戰者名稱!", Toast.LENGTH_SHORT).show()
            } else {
                var score: Int = Int.MAX_VALUE
                if (statecheck) {

                    db.collection("Users")
                        .whereEqualTo("使用者名稱", binding.user.text.toString())
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var msg: String = "查詢結果為:\n"
                                for (document in task.result!!) {
                                    score = document.data["時間"].toString().toInt()
                                }
                                if (finalscore < score) {
                                    user["使用者名稱"] = binding.user.text.toString()
                                    user["時間"] = finalscore
                                    db.collection("Users")
                                        .document(binding.user.text.toString())
                                        .set(user)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this, "上傳資料成功!\n成績為:" + finalscore,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                this, "上傳資料失敗：$e",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                } else {
                                    Toast.makeText(this, "你之前的成績更加優秀呢!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                } else {
                    Toast.makeText(this, "只有挑戰成功才能上傳成績喔!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun gameStatus(p0: View?) {
        gameStatus = !gameStatus    //=0
        finalscore = 0
        GlobalScope.launch(Dispatchers.Main) {
            while (gameStatus) {
                binding.test.text = binding.ball.ballSpeed_min.toString()
                //計時
                secondsCount++
                binding.time.text = (secondsCount / 35).toString()
                //遊戲結束判斷
                if (binding.ball.countBrick == 0 || gameEnd) {
                    gameStatus = false
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
                if (binding.ball.ballOrigY > 1300) {    //球觸底
                    gameStatus = false    //結束
                    gameEnd = true
                    statecheck = false
                } else if (binding.ball.countBrick == 0) {//方塊全破壞
                    gameStatus = false   //結束
                    finalscore = secondsCount / 35
                    gameEnd = true
                    statecheck = true
                }
                if (gameEnd && !gameStatus) {   //2
                    binding.button.text = "重新開始"
                    binding.ball.ballSpeed_min = 8
                    binding.ball.ballSpeed_min = 15
                    binding.ball.ballOrigX = 400
                    binding.ball.ballOrigY = 1000
                    binding.ball.ballMoveX = 0
                    binding.ball.ballMoveY = 8
                    binding.ball.detectBrick1 = true
                    binding.ball.detectBrick2 = true
                    binding.ball.detectBrick3 = true
                    binding.ball.detectBrick4 = true
                    binding.ball.detectBrick5 = true
                    binding.ball.detectBrick6 = true
                    binding.ball.countBrick = 6
                    secondsCount = 0
                    gameEnd = !gameEnd
                } else if (!gameEnd && gameStatus) {
                    binding.button.text = "暫停"
                } else if (!gameEnd && !gameStatus) {
                    binding.button.text = "繼續"
                }

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