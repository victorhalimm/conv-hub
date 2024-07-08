package edu.bluejack23_2.convhub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bluejack23_2.convhub.data.model.Job
import edu.bluejack23_2.convhub.data.repository.JobRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jobRepository: JobRepository,
) : ViewModel() {

    private val _jobState = MutableStateFlow<List<Job>>(emptyList())
    val jobState = _jobState.asStateFlow()

    init {
        fetchJobs()
    }

    private fun fetchJobs() {
        viewModelScope.launch {
            try {
                val jobs = jobRepository.fetchJobs()
                _jobState.value = jobs
            } catch (e: Exception) {
                // Handle error

            }
        }
    }

}
