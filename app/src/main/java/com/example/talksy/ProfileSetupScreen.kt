package com.example.talksy

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.talksy.viewmodel.ProfileViewModel

@Composable
fun ProfileSetupScreen(navController: NavController, viewModel: ProfileViewModel = ProfileViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var username by remember { mutableStateOf("") }
        var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }
        var isSaving by remember { mutableStateOf(false) }

        val context = LocalContext.current

        // Image Picker Launcher
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                val bitmap = getBitmapFromUri(context, uri)
                if (bitmap != null) {
                    profileBitmap = bitmap
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Set up your Profile",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium, // Using Material 3 typography
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Styled Username TextField (Material 3)
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
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
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Display or Select Profile Picture (Material 3)
            profileBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            // Launch the image picker
                            imagePickerLauncher.launch("image/*")
                        }
                )
            } ?: run {
                Text(
                    "Tap to select image",
                    style = MaterialTheme.typography.bodyMedium, // Use M3 typography
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        }
                        .padding(bottom = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Profile Button (Material 3)
            Button(
                onClick = {
                    if (profileBitmap != null && username.isNotBlank()) {
                        isSaving = true
                        viewModel.saveUserProfile(username, profileBitmap!!) { success ->
                            isSaving = false
                            if (success) navController.navigate("chat") { popUpTo("login") { inclusive = true } }
                            else { /* Handle failure */ }
                        }
                    }
                },
                enabled = !isSaving && profileBitmap != null && username.isNotBlank(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    if (isSaving) "Saving..." else "Save Profile",
                    style = MaterialTheme.typography.labelLarge // M3 typography for button text
                )
            }
        }
    }
}

// Improved getBitmapFromUri Function with Error Handling
fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
