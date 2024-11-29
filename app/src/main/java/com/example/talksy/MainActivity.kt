package com.example.talksy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.talksy.ui.theme.TalksyTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TalksyTheme {
                MainScreen() // Correctly wraps everything in a composable context
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val systemUiController = rememberSystemUiController()
    val topAppBarColor = MaterialTheme.colorScheme.primaryContainer

    // Set status bar color to match the TopAppBar color
    systemUiController.setStatusBarColor(
        color = topAppBarColor,
        darkIcons = false // Set to true if you want dark icons on a light background
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Talksy", style = MaterialTheme.typography.titleLarge) },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavigation()
            }
        }
    )
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("profileSetup") { ProfileSetupScreen(navController) }
        composable("chat") { ChatScreen(navController) }
    }
}
