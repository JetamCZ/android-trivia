package eu.puhony.trivia.data.quizResults

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import eu.puhony.trivia.data.quiz.Quiz
import eu.puhony.trivia.data.users.User

@Entity(
    tableName = "quiz_results",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Quiz::class,
            parentColumns = ["id"],
            childColumns = ["quizId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"]), Index(value = ["quizId"])]
)
data class QuizResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,
    val quizId: Int,

    val score: Int,
    val completedAt: Long
)
