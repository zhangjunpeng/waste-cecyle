package com.zjp.medicalwasterecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zjp.base.BaseActivity

import com.zjp.medicalwasterecycle.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }


}