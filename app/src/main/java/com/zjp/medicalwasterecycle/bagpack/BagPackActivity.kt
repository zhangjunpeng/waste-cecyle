package com.zjp.medicalwasterecycle.bagpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivityBagPackBinding

class BagPackActivity : BaseActivity() {

    lateinit var binding: ActivityBagPackBinding
    override fun setView() {
        binding= ActivityBagPackBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}