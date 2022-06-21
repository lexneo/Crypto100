package com.lexneoapps.crypto100.data.remote.response

import com.lexneoapps.crypto100.data.remote.model.Data
import com.lexneoapps.crypto100.data.remote.model.Status

data class NetworkResponse(
    val data: List<Data>,
    val status: Status
)