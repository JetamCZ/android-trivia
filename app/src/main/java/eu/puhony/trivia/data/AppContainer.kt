package eu.puhony.trivia.data

import android.content.Context
import eu.puhony.trivia.data.users.OfflineUsersRepository
import eu.puhony.trivia.data.users.UserDatabase
import eu.puhony.trivia.data.users.UsersRepository

interface AppContainer {
    val usersRepository: UsersRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(UserDatabase.getDatabase(context).itemDao())
    }
}
