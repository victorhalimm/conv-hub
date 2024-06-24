package edu.bluejack23_2.convhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.bluejack23_2.convhub.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater);
        setContentView(binding.root)
    }


}