package eu.puhony.trivia.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.puhony.trivia.TriviaApplication
import eu.puhony.trivia.data.Repository

class ListScreenViewModel(
    private val repository: Repository
) : ViewModel() {
    val allQuizes = repository.allQuizes

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TriviaApplication

                ListScreenViewModel(
                    repository = application.repository
                )
            }
        }
    }
}