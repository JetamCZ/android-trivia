package eu.puhony.trivia.ui.screens.resultScreen

import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun formatTimestamp(createdAt: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy HH:mm")
        .withZone(ZoneId.systemDefault()) // Adjusts to the device's timezone
    return formatter.format(Instant.ofEpochMilli(createdAt))
}