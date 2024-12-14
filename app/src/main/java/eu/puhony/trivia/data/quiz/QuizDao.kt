package eu.puhony.trivia.data.quiz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quiz: Quiz)

    @Query("SELECT * from quizes ORDER BY title ASC")
    fun getAll(): Flow<List<Quiz>>

    @Query("SELECT * from quizes WHERE id = :quizId")
    fun getById(quizId: Int): LiveData<Quiz>
}