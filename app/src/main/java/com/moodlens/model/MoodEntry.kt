package com.mcn.moodlens.model

data class MoodEntry(
    val id: String = "",
    val userId: String = "",
    val date: String = "",
    val mood: String = "",
    val moodLabel: String = "",
    val note: String = "",
    val stress: Int = 0,
    val energy: Int = 0,
    val sleep: Int = 0
)
