package com.example.talksy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.talksy.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = RegisterViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title Text for the Register Screen
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Styled Email TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = MaterialTheme.colorScheme.onSurface) },
                placeholder = { Text("Enter your email", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Styled Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = MaterialTheme.colorScheme.onSurface) },
                placeholder = { Text("Enter your password", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // Register Button with rounded corners and padding
            Button(
                onClick = {
                    viewModel.register(email, password) { success ->
                        if (success) {
                            navController.navigate("profileSetup") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            errorMessage = "Registration failed"
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Register",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // Display error message if present
            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
