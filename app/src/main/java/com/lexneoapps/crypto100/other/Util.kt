package com.lexneoapps.crypto100.other

import java.time.format.DateTimeFormatter

fun formatShortDate(isoString: String) : String{
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val date = formatter.parse(isoString)
    return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date)

}