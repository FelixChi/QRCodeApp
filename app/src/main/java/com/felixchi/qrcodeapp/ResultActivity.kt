package com.felixchi.qrcodeapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
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
//        supportActionBar?.displayOptions
        //Init View
        val format = intent.extras.getString(Intents.Scan.RESULT_FORMAT)
        findViewById<TextView>(R.id.tv_format).text = format
        resultTV = findViewById<TextView>(R.id.textv_result)
        _result = intent.extras.getString(Intents.Scan.RESULT)
        resultTV?.text = _result
        //
//        copy2Clipboard(_result)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_result_menu, menu)
        return true;
    }

    fun copy2Clipboard(text: String?) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.primaryClip = ClipData.newPlainText("code", text)
        Toast.makeText(this, "$text Copied", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        R.id.action_copy -> {
            //
            copy2Clipboard(_result)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
