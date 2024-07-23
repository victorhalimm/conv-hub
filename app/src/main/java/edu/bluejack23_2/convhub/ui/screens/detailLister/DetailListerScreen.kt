package edu.bluejack23_2.convhub.ui.screens.detailLister

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import edu.bluejack23_2.convhub.R
import edu.bluejack23_2.convhub.data.model.Job
import edu.bluejack23_2.convhub.data.model.User
import edu.bluejack23_2.convhub.ui.theme.ConvHubTheme
import edu.bluejack23_2.convhub.ui.theme.DarkBlue
import java.util.Date

@AndroidEntryPoint
class DetailListerScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jobId = intent.getStringExtra("jobId") ?: "jehoCTfD3EBWJ5YIYj78"
        setContent {
            ConvHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    JobDetailScreen(jobId)
                }
            }
        }
    }
}

@Composable
fun JobDetailScreen(jobId: String, viewModel: DetailListerViewModel = hiltViewModel()) {
    val jobDetail by viewModel.jobDetail.collectAsState()
    val applicants by viewModel.applicants.collectAsState()

    LaunchedEffect(jobId) {
        viewModel.loadJobDetail(jobId)
    }

    ConvHubTheme {
        LazyColumn {
            item {
                jobDetail?.let { job ->
                    JobDetailContent(
                        job,
                        applicants
                    )
                } ?: Text(text = "Loading...")
            }
        }
    }
}

@Composable
fun JobDetailContent(job: Job, applicants: List<User>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(DarkBlue)
        ) {
            Image(
                painter = rememberImagePainter(data = job.imageUris.firstOrNull()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable {
                        // Handle back action
                    }
                    .align(Alignment.TopStart)
            )
        }
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = job.title,
                fontSize = 24.sp,
                fontWeight = FontWeight(600)
            )
            Text(text = job.address, fontWeight = FontWeight(500), color = Color.Gray)
            Text(
                text = job.categories.joinToString(separator = " • ") { it },
                color = Color.Gray,
                fontWeight = FontWeight(500)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = job.description,
                    color = Color.Gray,
                    fontWeight = FontWeight(500)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Posted by ",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight(500)
                    )
                    Text(
                        text = job.jobLister,
                        fontSize = 14.sp,
                        color = DarkBlue,
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = "Star",
                        tint = Color.Yellow,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = job.rating.toString())
                }
            }
            Text(
                text = "Applicants",
                fontSize = 22.sp,
                fontWeight = FontWeight(600)
            )
            Spacer(modifier = Modifier.height(6.dp))
            applicants.forEach { applicant ->
                edu.bluejack23_2.convhub.ui.screens.detailTaker.ApplicationCard(applicant)
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun ApplicationCard(applicant: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
    ) {
        Text(text = applicant.username, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = applicant.email)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun JobDetailScreenPreview() {
    val mockJob = Job(
        id = "1",
        address = "123 Street",
        categories = listOf("Category1", "Category2"),
        imageUris = listOf("https://example.com/image1.jpg"),
        jobTaker = "JobTaker1",
        jobLister = "JobLister1",
        price = 100,
        status = "Open",
        title = "Mock Job Title",
        rating = 4.5f,
        description = "This is a mock job description. The job involves several detailed tasks that require a high level of skill and attention to detail. You will be responsible for managing multiple aspects of the project, including but not limited to task coordination, resource management, and ensuring timely delivery of all project components. Additionally, strong communication skills are necessary as you will be collaborating with various team members and stakeholders to ensure that all project requirements are met. This position offers a great opportunity for professional growth and development in a dynamic and fast-paced environment.",
        posted_at = java.util.Date()
    )

    val dummyUsers = listOf(
        User(
            dob = Date(1990, 1, 1),
            username = "Applicant1",
            id = "user1",
            email = "applicant1@example.com",
            picture = "https://example.com/user1.jpg",
            jobs = listOf("job1", "job2"),
            preferredFields = listOf("Technology", "Design")
        ),
        User(
            dob = Date(1992, 2, 2),
            username = "Applicant2",
            id = "user2",
            email = "applicant2@example.com",
            picture = "https://example.com/user2.jpg",
            jobs = listOf("job3", "job4"),
            preferredFields = listOf("Marketing", "Sales")
        ),
        User(
            dob = Date(1985, 3, 3),
            username = "Applicant3",
            id = "user3",
            email = "applicant3@example.com",
            picture = "https://example.com/user3.jpg",
            jobs = listOf("job5", "job6"),
            preferredFields = listOf("Engineering", "Management")
        ),
        User(
            dob = Date(1995, 4, 4),
            username = "Applicant4",
            id = "user4",
            email = "applicant4@example.com",
            picture = "https://example.com/user4.jpg",
            jobs = listOf("job7", "job8"),
            preferredFields = listOf("Finance", "Healthcare")
        )
    )
    ConvHubTheme {
       JobDetailContent(mockJob, dummyUsers)
    }
}
