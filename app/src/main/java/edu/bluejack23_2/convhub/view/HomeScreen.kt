package edu.bluejack23_2.convhub

import android.media.tv.TvContract.WatchNextPrograms
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.bluejack23_2.convhub.viewmodel.HomeState


@Preview
@Composable
fun HomeScreen(
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
            ) {
        Text("This text should be on the center!")
    }
}