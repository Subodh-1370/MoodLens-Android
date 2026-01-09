package com.moodlens.ui.screens.habit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HabitScreen() {
    val habits = remember {
        listOf(
            Habit("Drink Water", "💧"),
            Habit("2-Min Meditation", "🧘"),
            Habit("Walk 500 Steps", "🚶")
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Title
        Text(
            text = "Today's Habits",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Habits list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(habits) { habit ->
                HabitItem(habit = habit)
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit) {
    var isChecked by remember { mutableStateOf(false) }
    
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Emoji
            Text(
                text = habit.emoji,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 16.dp)
            )
            
            // Habit name
            Text(
                text = habit.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            
            // Checkbox
            IconButton(onClick = { isChecked = !isChecked }) {
                Icon(
                    imageVector = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                    contentDescription = if (isChecked) "Mark as incomplete" else "Mark as complete",
                    tint = if (isChecked) MaterialTheme.colorScheme.primary 
                          else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class Habit(
    val name: String,
    val emoji: String
)
