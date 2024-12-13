package eu.puhony.trivia.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.puhony.trivia.TriviaApplication
import eu.puhony.trivia.data.Repository
import kotlinx.coroutines.launch

class LoginScreenModel(
    private val repository: Repository
): ViewModel() {
    val allUsers = repository.allUsers //userDao.getAllUsers()

    var username by mutableStateOf("")
        private set


    fun setUserName(username: String) {
        this.username = username
    }

    fun createUserName() {
        val username = this.username

        viewModelScope.launch {
            repository.getOrCreateUser(username)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TriviaApplication

                LoginScreenModel(
                    repository = application.repository
                )
            }
        }
    }
}