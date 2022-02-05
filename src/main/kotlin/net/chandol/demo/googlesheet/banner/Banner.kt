package net.chandol.demo.googlesheet.banner

import java.time.LocalDateTime

data class Banner(
    val id: Long,
    val title: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)
