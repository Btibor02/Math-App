package com.example.mathquest.data

// --- FIREBASE IMPORTS COMMENTED OUT ---
// import android.content.Context
// import com.google.firebase.FirebaseApp
// import com.google.firebase.FirebaseOptions
// import com.google.firebase.firestore.DocumentSnapshot
// import com.google.firebase.firestore.FirebaseFirestore
// import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/**
* LeaderboardManager (Frontend-only version)
This version works completely offline and uses a hardcoded list of scores.
All Firebase / Firestore code is commented out for now.
Later, you can uncomment those sections to enable real database functionality.
*/


object LeaderboardManager {

data class ScoreEntry(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val score: Int,
    val timestamp: Long // epoch millis
)
// ---------- HARD-CODED SAMPLE SCORES (for local/dev/testing) ----------
private val hardcodedScores = mutableListOf(
    ScoreEntry(name = "Alice", score = 78, timestamp = 1698700000000L),
    ScoreEntry(name = "Ben",   score = 92, timestamp = 1698723000000L),
    ScoreEntry(name = "Cleo",  score = 85, timestamp = 1698738000000L),
    ScoreEntry(name = "Dex",   score = 60, timestamp = 1698750000000L),
    ScoreEntry(name = "Eve",   score = 99, timestamp = 1698762000000L),
    ScoreEntry(name = "Fiona", score = 74, timestamp = 1698774000000L),
    ScoreEntry(name = "Gus",   score = 88, timestamp = 1698786000000L)
)

// ---------- FIREBASE / FIRESTORE (commented out for now) ----------

/*
private var firestore: FirebaseFirestore? = null
private var initialized = false

fun init(context: Context) {
    if (initialized) return

    val options = FirebaseOptions.Builder()
        .setApiKey("REPLACE_WITH_API_KEY")
        .setApplicationId("REPLACE_WITH_APPLICATION_ID")
        .setProjectId("REPLACE_WITH_PROJECT_ID")
        .build()

    try {
        if (FirebaseApp.getApps(context).none { it.name == FirebaseApp.DEFAULT_APP_NAME }) {
            FirebaseApp.initializeApp(context, options)
        }
        firestore = FirebaseFirestore.getInstance()
        initialized = true
    } catch (e: Exception) {
        e.printStackTrace()
        initialized = false
        firestore = null
    }
}

private const val LEADERBOARD_COLLECTION = "leaderboard_entries"
*/

// ---------- PUBLIC API (works offline) ----------

/*Add a score to the local list.*/
fun addScore(scoreEntry: ScoreEntry) {
    synchronized(hardcodedScores) {
        hardcodedScores.add(scoreEntry)
    }
}

/*Suspend version (for use inside coroutines)*/
suspend fun addScoreSuspend(scoreEntry: ScoreEntry) = withContext(Dispatchers.IO) {
    addScore(scoreEntry)
}

/*Get the last N scores by timestamp (most recent).*/
fun getLastNScores(n: Int = 5): List<ScoreEntry> {
    return synchronized(hardcodedScores) {
        hardcodedScores.sortedByDescending { it.timestamp }.take(n)
    }
}

/*Get the lowest N scores (worst scores).*/
fun getLowestNScores(n: Int = 5): List<ScoreEntry> {
    return synchronized(hardcodedScores) {
        hardcodedScores.sortedBy { it.score }.take(n)
    }
}

/*Suspend variant.*/
suspend fun getLastNScoresSuspend(n: Int = 5): List<ScoreEntry> = withContext(Dispatchers.IO) {
    getLastNScores(n)
}

/*Get the best N scores (highest score).*/
fun getBestNScores(n: Int = 5): List<ScoreEntry> {
    return synchronized(hardcodedScores) {
        hardcodedScores.sortedByDescending { it.score }.take(n)
    }
}

/*Suspend variant.*/
suspend fun getBestNScoresSuspend(n: Int = 5): List<ScoreEntry> = withContext(Dispatchers.IO) {
    getBestNScores(n)
}

/* Get all local scores (copy).*/
fun getLocalSampleScores(): List<ScoreEntry> = synchronized(hardcodedScores) {
    hardcodedScores.toList()
}

/*Clear all local sample scores.*/

fun clearLocalSampleScores() = synchronized(hardcodedScores) {
    hardcodedScores.clear()
}
}
