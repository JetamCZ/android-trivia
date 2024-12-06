package eu.puhony.trivia

import android.app.Application
import eu.puhony.trivia.data.AppDatabase
import eu.puhony.trivia.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TriviaApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val repository: Repository by lazy { Repository(userDao = database.userDao()) }

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            database.clearAllTables()
        }

        android.util.Log.d("TriviaApplication", "TriviaApplication is initialized")
    }
}