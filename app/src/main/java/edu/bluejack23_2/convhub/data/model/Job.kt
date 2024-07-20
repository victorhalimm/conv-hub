package edu.bluejack23_2.convhub.data.model

import java.util.*

data class Job(
    val id : String,

    val address : String,

    val categories : List<String>,

    val imageUris : List<String>,

    val jobTaker : String,

    val jobLister : String,

    val price : Int,

    val status : String,

    val title : String,

    val description : String,

    val applicants : List<String>,

    val posted_at : Date
) {
    // No-argument constructor for Firestore
    constructor() : this("","", listOf(),listOf(), "", "", 0, "","", "", listOf(), Date())
}
