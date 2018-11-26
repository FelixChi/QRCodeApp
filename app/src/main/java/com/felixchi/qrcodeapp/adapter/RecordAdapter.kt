package com.felixchi.qrcodeapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felixchi.qrcodeapp.R
import com.felixchi.qrcodeapp.data.RecordData
import kotlinx.android.synthetic.main.record_item_layout.view.*

class RecordAdapter (var items: ArrayList<RecordData>, val context: Context): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.record_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvFormat?.text = items[position].format
        holder?.tvContent?.text = items[position].content
        holder?.tvDate?.text = items[position].date.toString()
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    //
    val tvFormat = view.textv_format
    val tvContent = view.textv_content
    val tvDate = view.textv_date
}