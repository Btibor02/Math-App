package com.example.mathquest.mathlogic 

import android.os.CountDownTimer

//export TimerLogic Obj and call with
//import com.example.mathquest.mathlogic.TimerLogic
// call TimerLogic.startTimer(60) starts 60s timer, any nr works

object TimerLogic{
    private var timer: CountDownTimer? = null

    fun startTimer(
        seconds: Int,
        onTick: ((Int) -> Unit)? = null,
        onFinish: (() -> Unit)? = null

        ){
        //stop existin timer
        timer?.cancel()

        timer = object : CountDownTimer(seconds * 1000L, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                onTick?.invoke((millisUntilFinished/ 1000).toInt())
            }

            override fun onFinish() {
                onFinish?.invoke()
            }
        }.start()
    }

    //call to stop timer
    fun stopTimer(){
        timer?.cancel()
        timer = null
    }
    //call to reset timer ex(when we solve nmore than 1 math problem with same timer option)
    fun resetTimer(
        seconds: Int,
        onTick: ((Int) -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ){
       stopTimer()
        startTimer(seconds, onTick, onFinish)
    }
}