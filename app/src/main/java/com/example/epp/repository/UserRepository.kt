package com.example.epp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

class UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

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
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e) }
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
        var year = ""
        var branch = ""
        var libraryId = ""
        var group = ""

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
                year = parts[1].substring(0, 4)
                branch = parts[1].substring(4, parts[1].length - 4)
                libraryId = parts[2].split("@")[0]
            }
        }
        return mapOf("name" to name, "year" to year, "branch" to branch, "libraryId" to libraryId, "group" to group)
    }
}