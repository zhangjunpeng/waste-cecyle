package com.zjp.medicalwasterecycle.outstock

import android.os.Bundle
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivitySelectComBinding

class SelectComActivity : BaseActivity() {
    private lateinit var binding: ActivitySelectComBinding

    override fun setView() {
        binding= ActivitySelectComBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}