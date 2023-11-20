package com.example.littlelemon.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.Onboarding
import com.example.littlelemon.persistence.SharedPreferences

@Composable
fun Navigation(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferences(context) }
    val startDestination =
        remember { if (sharedPrefs.isLoggedIn()) com.example.littlelemon.Home.route else Onboarding.route }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(com.example.littlelemon.Home.route) {
            Home(navController)
        }
        composable(com.example.littlelemon.Profile.route) {
            Profile(navController)
        }
    }
}