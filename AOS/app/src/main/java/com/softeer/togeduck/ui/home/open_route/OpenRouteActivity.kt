package com.softeer.togeduck.ui.home.open_route

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.softeer.togeduck.databinding.ActivityOpenRouteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenRouteActivity : AppCompatActivity() {
    private var _binding: ActivityOpenRouteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOpenRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}


