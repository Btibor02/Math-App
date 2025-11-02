package com.example.mathquest.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

data class Student(
    val uid: String = "",
    val username: String = "",
    val grade: String = "",
    val joinCode: String = "",
    val email: String = ""
)

data class Teacher(
    val uid: String = "",
    val username: String = "",
    val email: String = ""
)

data class ClassInfo(
    val id: String = "",
    val className : String = "",
    val joinCode: String = "",
    val teacherId: String = ""
)

class FirestoreService {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private val studentsRef = db.collection("students")
    private val teachersRef = db.collection("teachers")
    private val classesRef = db.collection("classes")

    suspend fun registerStudent(
        email: String,
        password: String,
        username: String,
        joinCode: String
    ): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Missing UID")

            val classInfo = getClassByJoinCode(joinCode)
                ?: throw Exception("Invalid join code")

            val newStudent = Student(uid, username, classInfo.className, joinCode, email)
            studentsRef.document(uid).set(newStudent).await()

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerTeacher(email: String, password: String, username: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Missing UID")

            val newTeacher = Teacher(uid, username, email)
            teachersRef.document(uid).set(newTeacher).await()

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<String> {
        return try {
            val res = auth.signInWithEmailAndPassword(email, password).await()
            val uid = res.user?.uid ?: throw Exception("Missing UID")

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createClass(className: String, joinCode: String, teacherId: String): Result<String> {
        return try {
            val docRef = classesRef.document()
            val newClass = ClassInfo(docRef.id, className, joinCode, teacherId)
            docRef.set(newClass).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTeacherById(uid: String): Teacher? {
        val snapshot = teachersRef.document(uid).get().await()
        return snapshot.toObject(Teacher::class.java)
    }

    suspend fun getStudentById(uid: String): Student? {
        val snapshot = studentsRef.document(uid).get().await()
        return snapshot.toObject(Student::class.java)
    }

    suspend fun getClassByJoinCode(joinCode: String): ClassInfo? {
        return try {
            val querySnapshot = Firebase.firestore.collection("classes")
                .whereEqualTo("joinCode", joinCode)
                .get()
                .await()
            if (querySnapshot.isEmpty) null
            else {
                val doc = querySnapshot.documents[0]
                ClassInfo(
                    id = doc.getString("id") ?: "",
                    className = doc.getString("className") ?: "",
                    joinCode = doc.getString("joinCode") ?: "",
                    teacherId = doc.getString("teacherId") ?: "",
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}