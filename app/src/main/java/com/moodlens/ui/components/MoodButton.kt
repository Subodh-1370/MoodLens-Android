package com.moodlens.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoodButton(
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    val borderWidth by animateDpAsState(
        targetValue = if (isSelected) 3.dp else 0.dp,
        label = "borderWidth"
    )
    
    val backgroundColor = if (isSelected) {
        color.copy(alpha = 0.2f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val borderColor = if (isSelected) {
        color
    } else {
        Color.Transparent
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoodButtonPreview() {
    MoodButton(
        emoji = "😄",
        isSelected = true,
        onClick = {},
        color = Color(0xFFFFD700)
    )
}
