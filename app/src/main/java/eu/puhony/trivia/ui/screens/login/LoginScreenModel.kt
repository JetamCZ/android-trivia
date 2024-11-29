package eu.puhony.trivia.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import eu.puhony.trivia.data.users.UsersRepository

class LoginScreenModel : ViewModel() {
   // private val usersRepository : UsersRepository = null

    var username by mutableStateOf("")
        private set


    fun setUserName(username: String) {
        this.username = username
    }
}