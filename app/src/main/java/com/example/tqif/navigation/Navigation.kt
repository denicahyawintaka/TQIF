package com.example.tqif

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tqif.Detail.DetailScreen
import com.example.tqif.navigation.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                    defaultValue = "sdasd"
                    nullable = true
                }
            )) { entry ->
            DetailScreen(username = entry.arguments?.getString("username"))
        }
    }
}
