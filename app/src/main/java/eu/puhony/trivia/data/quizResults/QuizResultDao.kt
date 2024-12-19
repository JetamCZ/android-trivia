package eu.puhony.trivia.data.quizResults

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(quizResult: QuizResult)

    @Query("SELECT * FROM quiz_results WHERE userId = :userId")
    suspend fun getResultsByUserId(userId: Int): List<QuizResult>

    @Query("SELECT * FROM quiz_results WHERE quizId = :quizId")
    suspend fun getResultsByQuizId(quizId: Int): List<QuizResult>
}