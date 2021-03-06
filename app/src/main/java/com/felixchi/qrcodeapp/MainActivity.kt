package com.felixchi.qrcodeapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.felixchi.qrcodeapp.data.RecordData
import com.felixchi.qrcodeapp.helper.PreferenceHelper
import com.felixchi.qrcodeapp.helper.PreferenceHelper.get
import com.felixchi.qrcodeapp.helper.PreferenceHelper.set
import com.google.gson.Gson
import com.google.gson.JsonArray

import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal var intentIntegrator: IntentIntegrator? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        //
        intentIntegrator = IntentIntegrator(this)
        var scannerImg: ImageView = findViewById(R.id.imgv_scanner)
        scannerImg.setOnClickListener {
            //goto Scanner activity
            gotoScanner()
        }
        //
        imgv_records.setOnClickListener {
            //goto records
            val intent = Intent(this@MainActivity, RecordListActivity::class.java)
            startActivity(intent)
        }

    }
    fun gotoScanner() {
        intentIntegrator?.setCaptureActivity(ScannerActivity::class.java)
            ?.setOrientationLocked(true)
            ?.initiateScan()
//        val intent = Intent(this@MainActivity, ScannerActivity::class.java)
//        startActivity(intent)
//        intentIntegrator?.setOrientationLocked(true)
//        intentIntegrator?.initiateScan()
    }
    // Save Scan result
    fun saveScanResult(result: IntentResult) {
        val prefs = PreferenceHelper.defaultPrefs(this)
        //
        var records:String ?= prefs["SCAN_RECORD"]
        val recordList: MutableList<RecordData> =
            if(!records.isNullOrEmpty()) {
                Gson().fromJson(records, Array<RecordData>::class.java).toMutableList()
            } else {
                mutableListOf()
            }
        //
        val  date = Calendar.getInstance().timeInMillis
        //Date Format:
        val dateString = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(date)
        Log.d("Scan", "$date > $dateString")
        val record = RecordData(result?.contents, result?.formatName, date, 1)
        recordList.add(record)
        records = Gson().toJson(recordList)
        //
        prefs["SCAN_RECORD"] = records;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result = IntentIntegrator.parseActivityResult(resultCode, data)
        Log.d("ScanCode", "Result: $result")
        if(result == null || result.contents == null) {
            Log.d("ScanCode","Scan Cancelled!!!")
        } else {
            Log.d("ScanCode", "Scan Complete")
            //show Scan result
            saveScanResult(result)
            var content = result.contents
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra(Intents.Scan.RESULT_FORMAT, result.formatName)
            intent.putExtra(Intents.Scan.RESULT, content)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
