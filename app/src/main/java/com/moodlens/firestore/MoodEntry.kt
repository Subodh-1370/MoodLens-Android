package com.mcn.moodlens.firestore

data class MoodEntry(
    val mood: String = "",
    val moodLabel: String = "",
    val note: String = "",
    val stress: Int = 0,
    val energy: Int = 0,
    val sleep: Int = 0,
    val timestamp: Long = 0L
)
