package edu.bluejack23_2.convhub.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_2.convhub.data.model.Job
import kotlinx.coroutines.tasks.await

class JobRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchJobs(): List<Job> {
        return db.collection("job")
            .get()
            .await()
            .toObjects(Job::class.java)
    }
}
