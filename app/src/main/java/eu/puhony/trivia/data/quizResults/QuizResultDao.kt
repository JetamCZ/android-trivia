package eu.puhony.trivia.data.quizResults

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.puhony.trivia.data.users.User

@Dao
interface QuizResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(quizResult: QuizResult): Long

    @Query("SELECT * FROM quiz_results WHERE userId = :userId")
    suspend fun getResultsByUserId(userId: Int): List<QuizResult>

    @Query("SELECT * FROM quiz_results WHERE userId = :userId AND  quizId = :quizId ORDER BY score DESC")
    suspend fun getResultsByUserIdAndQuizId(userId: Int, quizId: Int): List<QuizResult>

    @Query("SELECT * FROM quiz_results WHERE quizId = :quizId")
    suspend fun getResultsByQuizId(quizId: Int): List<QuizResult>

    @Query("""
    SELECT 
        users.*, 
        MAX(quiz_results.score) AS bestScore
    FROM quiz_results
    INNER JOIN users ON users.id = quiz_results.userId
    WHERE quiz_results.quizId = :quizId
    GROUP BY users.id
    ORDER BY bestScore DESC
""")
    suspend fun getBestScoresWithUsersByQuizId(quizId: Int): List<UserWithBestScore>


}

data class UserWithBestScore(
    @Embedded val user: User,
    val bestScore: Int
)
