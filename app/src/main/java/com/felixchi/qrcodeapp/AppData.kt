package com.felixchi.qrcodeapp.data

data class RecordData (
    val content: String? = null,
    val format: String?,
    val date: Long?,
    // Record type 1 = Scan 2 = Generate
    val type:Int? = 0
)
