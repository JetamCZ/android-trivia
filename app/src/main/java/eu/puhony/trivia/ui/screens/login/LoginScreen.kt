package eu.puhony.trivia.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginScreenModel = viewModel(factory = LoginScreenModel.Factory),
) {
    val users by viewModel.allUsers.collectAsState(initial = emptyList())

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 20.dp)) {
        Text(
            text = "Welcome in Trivia app",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )

        Text(
            text = "Select already existing user",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 40.dp)
        )

        Text(
            text = "Or login as a new user...",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 40.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.username,
            label = {
                Text(text = "Username")
            },
            onValueChange = {
                viewModel.setUserName(it)
            }
        )

        Button(
            onClick = {
                viewModel.createUserName()
                onLogin()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Continue as ${viewModel.username}")
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                Button(onClick = {}) {
                    Text(text = user.username)
                }
            }
        }
    }
}