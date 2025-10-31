package com.example.mathquest.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.firestore


data class UserProfile(
    val uid: String = "",
    val username: String = "",
    val grade: String = "",
    val joinCode: String = ""
)

class UserRepository{
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val users = db.collection("users")

    suspend fun registerWithEmail(email: String, password: String, profile: UserProfile): Result<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("UID missing")
            val userDoc = profile.copy(uid = uid)
            users.document(uid).set(userDoc).await()
            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<String> {
        return try {
            val res = auth.signInWithEmailAndPassword(email, password).await()
            val uid = res.user?.uid ?: throw Exception("UID missing")
            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}





