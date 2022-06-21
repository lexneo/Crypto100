package com.lexneoapps.crypto100.data.remote.model

import com.lexneoapps.crypto100.other.formatShortDate
import java.text.DateFormat

data class Data(
    val date_added: String,
    val id: Int,
    val name: String,
    val quote: Quote,
    val symbol: String,
    ){

    val shortDate: String
        get() = formatShortDate(date_added)
}

