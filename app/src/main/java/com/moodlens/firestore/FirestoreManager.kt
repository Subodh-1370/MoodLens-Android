package com.mcn.moodlens.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreManager {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // ✅ Save mood entry
    suspend fun saveMoodEntry(
        mood: String,
        moodLabel: String,
        note: String,
        stress: Int,
        energy: Int,
        sleep: Int
    ): Result<Unit> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User not logged in"))

            val data = hashMapOf(
                "mood" to mood,
                "moodLabel" to moodLabel,
                "note" to note,
                "stress" to stress,
                "energy" to energy,
                "sleep" to sleep,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("users")
                .document(uid)
                .collection("moods")
                .add(data)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ✅ Get mood history
    suspend fun getMoodHistory(): Result<List<MoodEntry>> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User not logged in"))

            val snapshot = db.collection("users")
                .document(uid)
                .collection("moods")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()

            val list = snapshot.documents.mapNotNull { doc ->
                val mood = doc.getString("mood") ?: return@mapNotNull null
                val moodLabel = doc.getString("moodLabel") ?: ""
                val note = doc.getString("note") ?: ""
                val stress = (doc.getLong("stress") ?: 0L).toInt()
                val energy = (doc.getLong("energy") ?: 0L).toInt()
                val sleep = (doc.getLong("sleep") ?: 0L).toInt()
                val timestamp = doc.getLong("timestamp") ?: 0L

                MoodEntry(
                    mood = mood,
                    moodLabel = moodLabel,
                    note = note,
                    stress = stress,
                    energy = energy,
                    sleep = sleep,
                    timestamp = timestamp
                )
            }

            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
