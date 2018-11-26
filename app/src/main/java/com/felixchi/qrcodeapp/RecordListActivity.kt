package com.felixchi.qrcodeapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.felixchi.qrcodeapp.adapter.RecordAdapter
import com.felixchi.qrcodeapp.data.RecordData
import com.felixchi.qrcodeapp.helper.PreferenceHelper
import com.felixchi.qrcodeapp.helper.PreferenceHelper.get

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_record_list.*

class RecordListActivity : AppCompatActivity() {

    var recordAdapter: RecordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)
        //toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //load records
        val json:String ?= PreferenceHelper.defaultPrefs(this)["SCAN_RECORD"]
        val recordList: ArrayList<RecordData> = Gson().fromJson(json, Array<RecordData>::class.java).toMutableList() as ArrayList<RecordData>
        //recyclerview
        rv_record_list.layoutManager = LinearLayoutManager(this)
        rv_record_list.adapter = RecordAdapter(recordList, this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
