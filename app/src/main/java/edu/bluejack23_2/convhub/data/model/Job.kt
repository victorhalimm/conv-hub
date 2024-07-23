package edu.bluejack23_2.convhub.data.model

import java.util.*

data class Job(
    val id: String = "",
    val address: String = "",
    val categories: List<String> = emptyList(),
    val imageUris: List<String> = emptyList(),
    val jobTaker: String? = null,
    val jobLister: String = "",
    val price: Int = 0,
    val status: String = "",
    val title: String = "",
    val rating: Float = 0f,
    val description: String = "",
    val applicants: List<Applicant> = emptyList(),
    val posted_at: Date = Date()
) {
    // No-argument constructor for Firestore
    constructor() : this(
        id = "",
        address = "",
        categories = emptyList(),
        imageUris = emptyList(),
        jobTaker = null,
        jobLister = "",
        price = 0,
        status = "",
        title = "",
        rating = 0f,
        description = "",
        applicants = emptyList(),
        posted_at = Date()
    )
}
