package eu.puhony.trivia.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenModel = viewModel(),
) {

    Column(modifier) {
        OutlinedTextField(
            value = viewModel.username,
            label = {
                Text(text = "Username")
            },
            onValueChange = {
                viewModel.setUserName(it)
            }
        )
    }
}