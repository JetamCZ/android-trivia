package eu.puhony.trivia.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import eu.puhony.trivia.ui.components.BigHeading

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginScreenModel = viewModel(factory = LoginScreenModel.Factory),
) {
    val users by viewModel.allUsers.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp)
    ) {

        BigHeading(
            title = "Welcome to the Trivia App"
        )

        Text(
            text = "Select an existing user",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 40.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    //elevation = CardElevation
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter("https://robohash.org/${user.username}"),
                            contentDescription = "User Avatar",
                            modifier = Modifier.size(50.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                        Text(
                            text = user.username,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )

                        Button(onClick = {
                            viewModel.continueAsUser(user, onLogin)
                        }) {
                            Text(text = "Continue")
                        }
                    }
                }
            }
        }

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
                viewModel.createUsername(onLogin)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add ${viewModel.username}")
        }
    }
}
