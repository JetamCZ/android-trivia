package eu.puhony.trivia.data.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizes")
data class Quiz (
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    val description: String,

    val title: String,
    val amount: Int?,
    val category: Int?,
    val difficulty: String?,
    val type: String?
)