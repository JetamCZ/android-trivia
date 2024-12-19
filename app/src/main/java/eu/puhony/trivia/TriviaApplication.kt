package eu.puhony.trivia

import android.app.Application
import eu.puhony.trivia.api.TriviaApi
import eu.puhony.trivia.data.AppDatabase
import eu.puhony.trivia.data.Repository
import eu.puhony.trivia.data.quiz.Quiz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TriviaApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    val api: TriviaApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TriviaApi::class.java)
    }

    val repository: Repository by lazy { Repository(
        userDao = database.userDao(),
        quizDao = database.quizDao(),
        quizResultDao = database.quizResultDao(),
        api = api
    ) }

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            //database.clearAllTables()

            database.quizDao().insert(Quiz(
                id = 0,
                title = "General knowlage",
                amount = 10, category = 9, difficulty = null, type = null,
                description = "Random general knowlage questions"
            ))

            database.quizDao().insert(Quiz(
                id = 1,
                title = "Geek comics",
                amount = 20, category = 29, difficulty = null, type = null,
                description = "Comics? Comics!"
            ))

            database.quizDao().insert(Quiz(
                id = 2,
                title = "Easy any",
                amount = 20, category = null, difficulty = null, type = null,
                description = "Some easy questions, no stress..."
            ))
        }

        android.util.Log.d("TriviaApplication", "TriviaApplication is initialized")
    }
}