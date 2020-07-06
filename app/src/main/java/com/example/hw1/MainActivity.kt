package com.example.hw1

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    val maxTimes = 5
    private var runMode = 0
    private var nowTimes = 0
    private var standAns = arrayOf<Int>()
    private var userAns = arrayOf(0,0,0,0,0)
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_normal ->
                    if (checked) {
                        runMode = 0
                    }
                R.id.radio_debug ->
                    if (checked) {
                        runMode = 1
                    }
            }
        }
    }

    private fun restartGame(){
        Log.i("Sky","Restart")
        standAns = arrayOf( (0..1).shuffled().last(),
                            (0..1).shuffled().last(),
                            (0..1).shuffled().last(),
                            (0..1).shuffled().last(),
                            (0..1).shuffled().last())
        Log.i("Sky","Answer : ${standAns[0]},${standAns[1]},${standAns[2]},${standAns[3]},${standAns[4]}")
        nowTimes = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        restartGame()
        val submitButton:Button = findViewById(R.id.submit_btn)
        val switchList = listOf<Switch>( findViewById(R.id.switch1),
                                        findViewById(R.id.switch2),
                                        findViewById(R.id.switch3),
                                        findViewById(R.id.switch4),
                                        findViewById(R.id.switch5))
        val cirList = listOf<ImageView>( findViewById(R.id.cir1),
                                         findViewById(R.id.cir2),
                                         findViewById(R.id.cir3),
                                         findViewById(R.id.cir4),
                                         findViewById(R.id.cir5))
        val appInfo = applicationInfo
        val blackId = resources.getIdentifier("black","mipmap",appInfo.packageName)
        val whiteId = resources.getIdentifier("white","mipmap",appInfo.packageName)

        for(i in 0..4) {
            switchList[i].setOnCheckedChangeListener { _, b ->
                Log.i("Sky","set switch $i to $b")
                if (b) {
                    cirList[i].setImageResource(blackId)
                    userAns[i] = 1
                } else {
                    cirList[i].setImageResource(whiteId)
                    userAns[i] = 0
                }
            }
        }

        submitButton.setOnClickListener {
            var correctNumbers = 0
            Log.i("Sky","begin matching")
            for(i in 0..4){
                if(userAns[i] == standAns[i]) {
                    Log.i("Sky","switch $i matched")
                    ++correctNumbers
                }
            }
            ++nowTimes
            Log.i("Sky","check correctNumbers : $correctNumbers ; now times : $nowTimes")
            if(correctNumbers != 5 && nowTimes != maxTimes){
                val msg = "你答对了 $correctNumbers 个"
                AlertDialog.Builder(this)
                    .setTitle("Notice")
                    .setMessage(msg)
                    .setPositiveButton("OK") { _, _ -> }
                    .create().show()
            } else if(correctNumbers == 5){
                val msg = resources.getString(R.string.win_text)
                AlertDialog.Builder(this)
                    .setTitle("Notice")
                    .setMessage(msg)
                    .setPositiveButton("OK") { _, _ -> }
                    .create().show()
                restartGame()
            } else if(nowTimes >= maxTimes && runMode == 0 ){
                val msg = resources.getString(R.string.lose_text)
                AlertDialog.Builder(this)
                    .setTitle("Notice")
                    .setMessage(msg)
                    .setPositiveButton("OK") { _, _ -> }
                    .create().show()
                restartGame()
            }
        }

    }
}