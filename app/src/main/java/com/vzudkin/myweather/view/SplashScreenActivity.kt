package com.vzudkin.myweather.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar
import com.vzudkin.myweather.R

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initView()
        splashScreen()
    }

    private fun initView() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title)
        supportActionBar?.hide()
    }

    private fun splashScreen() {
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, SplashProgressActivity::class.java)
            startActivity(intent)
        }, 1500)
    }

}