package com.example.talksy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.talksy.data.Message
import com.example.talksy.viewmodel.ChatViewModel


@Composable
fun ChatScreen(navController: NavController, viewModel: ChatViewModel = ChatViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var messageText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Chat",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    label = { Text("Type a message", color = MaterialTheme.colorScheme.onSurface) },
                    placeholder = { Text("Say something...", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    maxLines = 3,
                    shape = MaterialTheme.shapes.medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage("chat_1", Message("user_1", messageText, System.currentTimeMillis()))
                        messageText = ""
                    }
                },
                enabled = messageText.isNotBlank(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (messageText.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (messageText.isNotBlank()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = "Send",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
