package com.moodlens.ui.screens.habit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Habit(
    val name: String,
    val emoji: String
)

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
        Text(
            text = "Today's Habits",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(habits) { habit ->
                HabitItem(habit)
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit) {
    var isChecked by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = habit.emoji, style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = habit.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            IconButton(onClick = { isChecked = !isChecked }) {
                Icon(
                    imageVector = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = null
                )
            }
        }
    }
}
