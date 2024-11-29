package eu.puhony.trivia.data.users

import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val itemDao: UserDao) : UsersRepository {
    override fun getAllUsersStream(): Flow<List<User>> = itemDao.getAllUsers()

    override fun getUserStream(id: Int): Flow<User?> = itemDao.getUser(id)

    override suspend fun insertUser(user: User) = itemDao.insert(user)

    override suspend fun deleteUser(user: User) = itemDao.delete(user)

    override suspend fun updateUser(user: User) = itemDao.update(user)
}
