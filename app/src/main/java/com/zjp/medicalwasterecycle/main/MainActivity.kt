package com.zjp.medicalwasterecycle.main

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.zjp.base.BaseActivity

import com.zjp.medicalwasterecycle.databinding.ActivityMainBinding
import com.zjp.viewmodel.MyViewModelFactory

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun setView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initViewModel() {
        mainViewModel =
            ViewModelProvider(this, MyViewModelFactory.instance).get(MainViewModel::class.java)
    }


}