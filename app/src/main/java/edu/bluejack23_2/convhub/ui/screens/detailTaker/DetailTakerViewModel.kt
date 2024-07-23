package edu.bluejack23_2.convhub.ui.screens.detailTaker


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bluejack23_2.convhub.data.model.Job
import edu.bluejack23_2.convhub.data.model.User
import edu.bluejack23_2.convhub.data.repository.JobRepository
import edu.bluejack23_2.convhub.data.repository.UserRepository
import edu.bluejack23_2.convhub.ui.events.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTakerViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _jobDetail = MutableStateFlow<Job?>(null)
    val jobDetail: StateFlow<Job?> get() = _jobDetail

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun loadJobDetail(jobId: String) {
        viewModelScope.launch {
            val job = jobRepository.getJobById(jobId)
            job?.let {
                val username = userRepository.fetchUsernameByUid(it.jobLister)
                val updatedJob = it.copy(jobLister = username ?: it.jobLister)
                _jobDetail.value = updatedJob
            } ?: run {
                _jobDetail.value = null
            }
        }
    }

    fun applyJob(jobId : String, userId : String) {
        viewModelScope.launch {
            try {
                jobRepository.applyJobByUserId(jobId, userId)
                _uiEvent.emit(UiEvent.ShowToast("Successfully applied for job!"))
            } catch (e : Exception) {
                _uiEvent.emit(UiEvent.ShowToast("Apply for job error!"))
            }
        }
    }


}
