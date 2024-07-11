package com.sewain.mobileapp.utils

// Extensions.kt
import java.text.NumberFormat
import java.util.Locale

fun Int.rp(): String {
    val localeID = Locale("in", "ID")
    val formatter = NumberFormat.getCurrencyInstance(localeID).apply {
        maximumFractionDigits = 0 // Set the maximum fraction digits to zero
    }
    return formatter.format(this)
}

fun Double.rp(): String {
    val localeID = Locale("in", "ID")
    val formatter = NumberFormat.getCurrencyInstance(localeID).apply {
        maximumFractionDigits = 0 // Set the maximum fraction digits to zero
    }
    return formatter.format(this)
}
