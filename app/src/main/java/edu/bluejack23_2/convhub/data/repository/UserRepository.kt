package edu.bluejack23_2.convhub.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_2.convhub.data.model.User
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchJobs(): List<User> {
        return db.collection("user")
            .get()
            .await()
            .toObjects(User::class.java)
    }
}