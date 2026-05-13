package ua.nure.smartlightadmin.extension

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant
import kotlin.math.roundToInt

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
private fun Float.format(decimals: Int): String {
    return if (decimals == 0) {
        this.roundToInt().toString()
    } else {
        val factor = 10f * decimals  // 10^decimals
        val rounded = (this * factor).roundToInt() / factor
        val intPart = rounded.toInt()
        val fracPart = ((rounded - intPart) * factor).roundToInt()
        "${intPart}.${fracPart.toString().padStart(decimals, '0')}"
    }
}

fun Long?.toPrettyBytes(): String =
    when {
        this == null -> "0 B"
        this <= 1024L -> "$this B"
        this in 1025L..<1_048_576L -> "${(this.toFloat() / 1024).format(0)} kB"
        this in 1_048_576L..<1_073_741_824L -> "${(this.toFloat() / 1_048_576).format(1)} MB"
        else -> "${(this.toFloat() / 1_073_741_824).format(1)} GB"
    }

fun Long.toPrettyString(): String =
    when {
        this <= 999L -> this.toString()
        this in 1_000L..<999_000L -> "${(this.toFloat() / 1_000).format(1)}k"
        this in 999_000L..<999_000_000L -> "${(this.toFloat() / 1_000_000).format(1)}M"
        else -> "${(this.toFloat() / 1_000_000_000).format(1)}bn"
    }