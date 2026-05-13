package ua.nure.smartlight.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime
        .ofInstant(
            Instant.ofEpochSecond(this), ZoneId.systemDefault()
        )