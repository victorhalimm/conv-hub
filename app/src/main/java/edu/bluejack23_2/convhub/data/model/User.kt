package edu.bluejack23_2.convhub.data.model

import java.util.Date

data class User(
    val dob: Date = Date(),
    val username: String = "",
    val id: String = "",
    val email: String = "",
    val picture: String = "",
    val jobs: List<String> = listOf() // Provide an empty list as the default value
) {
    // No-argument constructor for Firestore
    constructor() : this(Date(), "", "", "", "", listOf())
}
