package edu.bluejack23_2.convhub.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_2.convhub.data.model.Job
import edu.bluejack23_2.convhub.di.RepositoryModule
import kotlinx.coroutines.tasks.await

class JobRepository {
    private val db = FirebaseFirestore.getInstance()
    private val userRepository = RepositoryModule.provideUserRepository()
    suspend fun fetchJobs(): List<Job> {
        return try {
            val jobDocuments = db.collection("job").get().await().documents
            val jobs = jobDocuments.mapNotNull { document ->
                val job = document.toObject(Job::class.java)
                if (job != null) {
                    val username = userRepository.fetchUsernameByUid(job.jobLister)
                    job.copy(jobLister = username ?: job.jobLister)
                } else {
                    null
                }
            }
            jobs
        } catch (e: Exception) {
            Log.e("JobRepository", "Error fetching jobs", e)
            emptyList()
        }
    }

    suspend fun fetchJobsByPreferredFields(preferredFields: List<String>): List<Job> {
        val allJobs = fetchJobs()
        return allJobs.filter { job ->
            preferredFields.isEmpty() || job.categories.any { it in preferredFields }
        }
    }


}
