package com.zjp.medicalwasterecycle.outstock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivityOutHisBinding

class OutHisActivity : BaseActivity() {

    private lateinit var binding:ActivityOutHisBinding

    override fun setView() {
        binding= ActivityOutHisBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}