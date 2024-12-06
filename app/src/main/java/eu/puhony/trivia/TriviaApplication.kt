package eu.puhony.trivia

import android.app.Application
import eu.puhony.trivia.data.users.AppDatabase

class TriviaApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        android.util.Log.d("TriviaApplication", "TriviaApplication is initialized")
    }
}