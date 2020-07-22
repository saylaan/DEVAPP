package com.epicture.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.epicture.app.ui.Login

class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val b = findViewById<Button>(R.id.start_button)
        val imgIcon = findViewById<ImageView>(R.id.logo_imageView)

        val clickListener = View.OnClickListener {
            // Login
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }

        val clickListenerIcon = View.OnClickListener {
            imgIcon.animate().rotationBy(360f).setDuration(3000)
                .setInterpolator(LinearInterpolator()).start()
        }

        b.setOnClickListener(clickListener)
        imgIcon.setOnClickListener(clickListenerIcon)
    }
}