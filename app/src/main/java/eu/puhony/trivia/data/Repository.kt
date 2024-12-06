package eu.puhony.trivia.data

import eu.puhony.trivia.data.users.User
import eu.puhony.trivia.data.users.UserDao
import kotlinx.coroutines.flow.Flow

class Repository(private val userDao: UserDao) {
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun getOrCreateUser(username: String): User {
        // Check if user exists
        val existingUser = userDao.getUserByUsername(username)

        if (existingUser != null) {
            return existingUser
        }

        // Create and insert a new user
        val newUser = User(username = username)
        val userId = userDao.insert(newUser)
        return newUser //.copy(id = userId.toString().toInt())
    }
}