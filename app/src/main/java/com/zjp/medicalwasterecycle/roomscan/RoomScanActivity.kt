package com.zjp.medicalwasterecycle.roomscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivityRoomScanBinding

class RoomScanActivity : BaseActivity() {

    lateinit var binding: ActivityRoomScanBinding

    override fun setView() {
        binding=ActivityRoomScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}