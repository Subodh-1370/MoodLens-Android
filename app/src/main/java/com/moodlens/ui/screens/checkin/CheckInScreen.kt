package com.moodlens.ui.screens.checkin

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mcn.moodlens.firestore.FirestoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class MoodOption(val emoji: String, val label: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInScreen(
    onBack: (() -> Unit)? = null,
    onSave: (() -> Unit)? = null
) {
    val moods = listOf(
        MoodOption("😄", "Great"),
        MoodOption("🙂", "Good"),
        MoodOption("😐", "Okay"),
        MoodOption("😔", "Low"),
        MoodOption("😡", "Angry")
    )

    var selectedMood by remember { mutableStateOf(moods[1]) }
    var note by remember { mutableStateOf("") }

    var stress by remember { mutableStateOf(30f) }
    var energy by remember { mutableStateOf(60f) }
    var sleep by remember { mutableStateOf(50f) }

    // ✅ Firebase logic
    val scope = rememberCoroutineScope()
    val firestoreManager = remember { FirestoreManager() }

    var isSaving by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var successMsg by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Daily Check-in") },
                navigationIcon = {
                    if (onBack != null) {
                        TextButton(onClick = { onBack() }) { Text("Back") }
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // Header Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How are you feeling today?",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Be honest with yourself. Small progress daily 💪",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }

            // Mood Row
            Text(
                text = "Pick your mood",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(moods) { mood ->
                    MoodChip(
                        mood = mood,
                        isSelected = mood == selectedMood,
                        onClick = {
                            selectedMood = mood
                            successMsg = null
                            errorMsg = null
                        }
                    )
                }
            }

            // Note
            OutlinedTextField(
                value = note,
                onValueChange = {
                    note = it
                    successMsg = null
                    errorMsg = null
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                label = { Text("Write a short note (optional)") },
                supportingText = { Text("Example: “Exams stress but I did workout”") },
                maxLines = 3,
                enabled = !isSaving
            )

            // Sliders area
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 3.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    MetricSlider(
                        title = "Stress",
                        icon = Icons.Default.LocalFireDepartment,
                        value = stress,
                        onValueChange = {
                            stress = it
                            successMsg = null
                            errorMsg = null
                        },
                        enabled = !isSaving
                    )

                    MetricSlider(
                        title = "Energy",
                        icon = Icons.Default.Bolt,
                        value = energy,
                        onValueChange = {
                            energy = it
                            successMsg = null
                            errorMsg = null
                        },
                        enabled = !isSaving
                    )

                    MetricSlider(
                        title = "Sleep Quality",
                        icon = Icons.Default.Bedtime,
                        value = sleep,
                        onValueChange = {
                            sleep = it
                            successMsg = null
                            errorMsg = null
                        },
                        enabled = !isSaving
                    )
                }
            }

            // ✅ Status messages
            errorMsg?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            successMsg?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // ✅ Save button (Firebase connected)
            Button(
                onClick = {
                    scope.launch {
                        isSaving = true
                        errorMsg = null
                        successMsg = null

                        val result = firestoreManager.saveMoodEntry(
                            mood = selectedMood.emoji,
                            moodLabel = selectedMood.label,
                            note = note.trim(),
                            stress = stress.toInt(),
                            energy = energy.toInt(),
                            sleep = sleep.toInt()
                        )

                        isSaving = false

                        result.onSuccess {
                            successMsg = "Saved successfully ✅"
                            delay(400)
                            onSave?.invoke()
                        }.onFailure { e ->
                            errorMsg = e.message ?: "Failed to save check-in"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Saving...", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                } else {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(Modifier.width(10.dp))
                    Text("Save Check-in", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            // Footer small feedback
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "Selected mood: ${selectedMood.emoji} (${selectedMood.label})",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun MoodChip(
    mood: MoodOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bg by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        label = "moodChipColor"
    )

    val content by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        label = "moodChipTextColor"
    )

    Surface(
        modifier = Modifier
            .shadow(if (isSelected) 8.dp else 1.dp, CircleShape)
            .clip(CircleShape)
            .clickable { onClick() },
        shape = CircleShape,
        color = bg,
        border = if (!isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(mood.emoji, fontSize = 22.sp)
            Spacer(Modifier.height(4.dp))
            Text(mood.label, color = content, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun MetricSlider(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: Float,
    onValueChange: (Float) -> Unit,
    enabled: Boolean
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(icon, contentDescription = null)
            Spacer(Modifier.width(10.dp))
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Text("${value.toInt()}%", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            enabled = enabled
        )
    }
}
