package eu.puhony.trivia.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eu.puhony.trivia.data.quiz.Quiz
import eu.puhony.trivia.data.quiz.QuizDao
import eu.puhony.trivia.data.quizResults.QuizResult
import eu.puhony.trivia.data.quizResults.QuizResultDao
import eu.puhony.trivia.data.users.User
import eu.puhony.trivia.data.users.UserDao

@Database(entities = [User::class, Quiz::class, QuizResult::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun quizDao(): QuizDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "trivia_database")
                    //.addMigrations(MIGRATION_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

/*
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Custom migration logic here
    }
}
 */
