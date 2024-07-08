package edu.bluejack23_2.convhub.data.model

data class Job(
    val id : String,

    val address : String,

    val categories : List<String>,

    val imageUris : List<String>,

    val jobTaker : User?,

    val jobLister : User,

    val price : Int,

    val status : String,

    val title : String,
)
