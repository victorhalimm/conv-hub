package edu.bluejack23_2.convhub.ui.screens.detailLister


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bluejack23_2.convhub.data.model.Applicant
import edu.bluejack23_2.convhub.data.model.Job
import edu.bluejack23_2.convhub.data.model.User
import edu.bluejack23_2.convhub.data.repository.JobRepository
import edu.bluejack23_2.convhub.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailListerViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _jobDetail = MutableStateFlow<Job?>(null)
    val jobDetail: StateFlow<Job?> get() = _jobDetail

    private val _applicants = MutableStateFlow<List<User>>(emptyList())
    val applicants: StateFlow<List<User>> get() = _applicants

    fun loadJobDetail(jobId: String) {
        viewModelScope.launch {
            val (job, users) = jobRepository.fetchJobWithApplicants(jobId)
            job?.let {
                val username = userRepository.fetchUsernameByUid(it.jobLister)
                val updatedJob = it.copy(jobLister = username ?: it.jobLister)
                _jobDetail.value = updatedJob
            } ?: run {
                _jobDetail.value = null
            }
            _applicants.value = users
        }

        jobRepository.addJobApplicantsListener(jobId, viewModelScope) { users ->
            _applicants.value = users
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobRepository.removeJobApplicantsListener()
    }

}
