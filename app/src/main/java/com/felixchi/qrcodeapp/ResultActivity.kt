package com.felixchi.qrcodeapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.zxing.client.android.Intents
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    var resultTV: TextView ?= null
    var _result: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //Init View
        val format = intent.extras.getString(Intents.Scan.RESULT_FORMAT)
        findViewById<TextView>(R.id.tv_format).text = format
        resultTV = findViewById<TextView>(R.id.textv_result)
        _result = intent.extras.getString(Intents.Scan.RESULT)
        resultTV?.text = _result
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
