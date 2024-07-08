package edu.bluejack23_2.convhub.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.bluejack23_2.convhub.ui.viewmodel.HomeViewModel
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val jobState by viewModel.jobState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (jobState.isEmpty()) {
            Text(
                text = "No Data Available",
                fontSize = 12.sp,
            )
        } else {
            LazyColumn {
                items(jobState) { job ->
                    Text(
                        text = job.title,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
