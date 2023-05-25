package com.example.valorpay.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.valorpay.R
import com.example.valorpay.util.receiver.ConnectionLiveData

class NoInternetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)
        val connectionLiveData = ConnectionLiveData(this)

        connectionLiveData.observe(this, Observer {
            if (it) {
                finish()
            }
        })

    }
}