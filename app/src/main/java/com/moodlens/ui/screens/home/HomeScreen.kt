package com.moodlens.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.moodlens.ui.components.MoodButton
import com.moodlens.ui.theme.*

@Composable
fun HomeScreen() {
    var selectedMood by remember { mutableStateOf<Int?>(null) }
    var note by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Greeting
        Text(
            text = "Hi 👋",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Mood question
        Text(
            text = "How are you feeling today?",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Mood buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MoodButton(
                emoji = "😄",
                isSelected = selectedMood == 0,
                onClick = { selectedMood = 0 },
                color = HappyColor
            )
            MoodButton(
                emoji = "🙂",
                isSelected = selectedMood == 1,
                onClick = { selectedMood = 1 },
                color = GoodColor
            )
            MoodButton(
                emoji = "😐",
                isSelected = selectedMood == 2,
                onClick = { selectedMood = 2 },
                color = NeutralColor
            )
            MoodButton(
                emoji = "😔",
                isSelected = selectedMood == 3,
                onClick = { selectedMood = 3 },
                color = SadColor
            )
            MoodButton(
                emoji = "😣",
                isSelected = selectedMood == 4,
                onClick = { selectedMood = 4 },
                color = AwfulColor
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Optional note
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Write how you feel...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Note"
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 4
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Submit button
        Button(
            onClick = { /* Handle submission */ },
            enabled = selectedMood != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Log My Mood")
        }
    }
}
