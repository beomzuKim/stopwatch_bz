package com.example.stopwatch_bz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // start
        fab.setOnClickListener{
            isRunning = !isRunning
            if(isRunning){
                start()
            }else{
                pause()
            }
        }
        // record
        button.setOnClickListener{
            recordLapTime()
        }

        resetFab.setOnClickListener{
            reset()
        }
    }
//############################################################################################################
    private fun start(){
        fab.setImageResource(R.drawable.ic_baseline_pause_24) // 재생을 눌렀을시에 pause로 로고가 바
        timerTask = timer(period = 10){
            time++ // 1초씩 증가.

            val sec = time / 100
            val milli = time % 100
            runOnUiThread{
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(){
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()
    }

    private fun recordLapTime(){
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime%100}"

        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset(){
        timerTask?.cancel()
        lapLayout.removeAllViews()
        lap = 1

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secTextView.text = "0"
        milliTextView.text = "00"


    }
}