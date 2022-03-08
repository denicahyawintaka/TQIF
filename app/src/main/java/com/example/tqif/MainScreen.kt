package com.example.tqif

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.tqif.model.User
import com.example.tqif.navigation.Screen
import com.example.tqif.ui.theme.PurpleLight20

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar()

        when (val state = mainViewModel.mainViewState.value) {
            is MainViewState.Success -> {
                UserList(users = state.users, navController)
            }
            is MainViewState.Loading -> {
                CircularProgressIndicator()
            }
            is MainViewState.Error -> {
                Icon(
                    Icons.Default.Warning,
                    "contentDescription"
                )
            }
            is MainViewState.Idle -> {
                Image(
                    painter = rememberImagePainter(R.drawable.github),
                    contentDescription = "github image",
                )
            }
        }
    }
}

@Composable
fun SearchBar(mainViewModel: MainViewModel = viewModel()) {
    var username: String by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = username,
        onValueChange = {
            username = it
        },
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                "contentDescription"
            )
        },
        label = { Text("Username", style = MaterialTheme.typography.subtitle1) },
        maxLines = 1,
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search, // ** Go to next **
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                mainViewModel.findUser(username)
            })
    )
}

@Composable
fun UserList(users: List<User>, navController: NavController? = null) {
    LazyRow {
        items(users) { user ->
            UserRow(user = user, navController)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserRow(user: User, navController: NavController? = null) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(PurpleLight20)
            .clickable {
                navController?.navigate(
                    Screen.DetailScreen.withArgs(
                        user.username
                    )
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(user.avatarUrl),
            contentDescription = "avatar",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 8.dp)
                .clip(CircleShape)
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = user.username,
            style = MaterialTheme.typography.h5
        )
    }
}
