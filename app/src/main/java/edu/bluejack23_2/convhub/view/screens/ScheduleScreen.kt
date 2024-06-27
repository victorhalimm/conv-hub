package edu.bluejack23_2.convhub.view.screens


import android.media.tv.TvContract.WatchNextPrograms
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.bluejack23_2.convhub.viewmodel.HomeState


@Composable
fun ScheduleScreen(
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Schedule Screen",
            fontSize = 12.sp,
        )
    }
}