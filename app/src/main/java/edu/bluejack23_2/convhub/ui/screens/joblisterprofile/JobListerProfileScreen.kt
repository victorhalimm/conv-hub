package edu.bluejack23_2.convhub.ui.screens.joblisterprofile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import edu.bluejack23_2.convhub.ui.screens.joblisterprofile.ui.theme.ConvHubTheme
import edu.bluejack23_2.convhub.ui.screens.jobtakerprofile.JobTakerProfileScreen
import edu.bluejack23_2.convhub.ui.screens.jobtakerprofile.JobTakerProfileViewModel
import edu.bluejack23_2.convhub.ui.theme.screens.JobCard
import edu.bluejack23_2.convhub.ui.viewmodel.ProfileViewModel

@AndroidEntryPoint
class JobListerProfileActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("userId") ?: return
        setContent {
            edu.bluejack23_2.convhub.ui.screens.jobtakerprofile.ui.theme.ConvHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    JobListerProfileScreen(userId)
                }
            }
        }
    }
}

@Composable
fun JobListerProfileScreen(
    userId: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    JobTakerProfileViewModel: JobTakerProfileViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()


    val jobs by JobTakerProfileViewModel.jobs.collectAsState()
    val availableJobs by JobTakerProfileViewModel.availablejobs.collectAsState()
    val previousJobs by JobTakerProfileViewModel.previousjobs.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
        Log.d("JobListerProfileScreen", "Fetching jobs for user: $userId")
        JobTakerProfileViewModel.fetchAvailableJobs(userId)
        JobTakerProfileViewModel.fetchPreviousJobs(userId)

    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Job Lister Details",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Image(
            painter = rememberAsyncImagePainter(model = userState.picture),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${userState.username}",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Available Jobs",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp)
        ) {
            items(availableJobs) { job ->
                JobCard(job)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Previously Posted Jobs",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(4.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp)
        ) {
            items(previousJobs) { job ->
                JobCard(job)
            }
        }
    }
}