package com.example.epp.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale
import java.util.regex.Pattern

class UserRepository(context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    fun register(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (!isValidEmail(email)) {
            onFailure(Exception("Invalid email format"))
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userInfo = parseEmail(email)
                    db.collection("users").document(user!!.uid).set(userInfo)
                        .addOnSuccessListener {
                            sharedPref.edit().putBoolean("is_logged_in", true).apply()
                            onSuccess() }
                        .addOnFailureListener { e -> onFailure(e) }
                } else {
                    onFailure(task.exception!!)
                }
            }
    }
    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedPref.edit().putBoolean("is_logged_in", true).apply()
                    onSuccess()
                } else {
                    onFailure(task.exception!!)
                }
            }
    }


    private fun isValidEmail(email: String): Boolean {
        val regex = "^[a-zA-Z]+\\.\\d{4}[a-zA-Z]+\\d{4}@kiet.edu$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(email).matches()
    }

    private fun parseEmail(email: String): Map<String, String> {
        val parts = email.split(".")
        val name = parts[0]
        var batch = ""
        var branch = ""
        var libraryId = ""
        val group: String

        when {
            email.contains("dean") -> {
                group = "Deans"
            }
            email.contains("hod") -> {
                group = "HoDs"
                branch = parts[1].split("@")[0]
            }
            else -> {
                group = "Students"
                batch = "20"+parts[1].substring(0, 2)+"-"+parts[1].substring(2, 4)
                branch = parts[1].substring(4, parts[1].length - 9).uppercase(Locale.ROOT)
                libraryId = parts[1].substring(0, parts[1].length - 5)
            }
        }
        return mapOf("name" to name, "batch" to batch, "branch" to branch, "libraryId" to libraryId, "group" to group)
    }
    fun logout() {
        sharedPref.edit().putBoolean("is_logged_in", false).apply()
        auth.signOut()
    }
    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("is_logged_in", false)
    }
    fun verifyEmail(email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    db.collection("users")
        .whereEqualTo("email", email)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result
                if (!documents.isEmpty) {
                    onSuccess()
                } else {
                    onFailure(Exception("Email does not exist"))
                }
            } else {
                onFailure(task.exception!!)
            }
        }
}
    fun updatePassword(password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val user = auth.currentUser
        user!!.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception!!)
                }
            }
    }
}