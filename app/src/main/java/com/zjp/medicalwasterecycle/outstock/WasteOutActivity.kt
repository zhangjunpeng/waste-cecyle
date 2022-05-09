package com.zjp.medicalwasterecycle.outstock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivitySelectComBinding
import com.zjp.medicalwasterecycle.databinding.ActivityWasteOutBinding

class WasteOutActivity : BaseActivity() {
    private lateinit var binding: ActivityWasteOutBinding

    override fun setView() {
        binding= ActivityWasteOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}