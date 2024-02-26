package com.softeer.togeduck.ui.home.seat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.bumptech.glide.Glide.init
import com.softeer.togeduck.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatActivity : AppCompatActivity() {
    private val args: SeatActivityArgs by navArgs()
    private val seatViewModel: SeatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)

        init()
    }

    private fun init() {
        /////////////////// 추후 수정 필요
//        val routeId = args.routeId
        val routeId = 1
        seatViewModel.routeId = routeId
    }
}