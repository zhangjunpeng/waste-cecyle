package com.zjp.medicalwasterecycle.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.account.AccountActivity
import com.zjp.medicalwasterecycle.bagpack.BagPackActivity

import com.zjp.medicalwasterecycle.databinding.ActivityMainBinding
import com.zjp.medicalwasterecycle.outstock.OutHisActivity
import com.zjp.medicalwasterecycle.outstock.WasteOutActivity
import com.zjp.viewmodel.MyViewModelFactory

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.inLayoutMain.setOnClickListener {
            startActivity(Intent(this,BagPackActivity::class.java))
        }
        binding.outLayoutMain.setOnClickListener {
            startActivity(Intent(this,WasteOutActivity::class.java))
        }
        binding.unselectLayout.setOnClickListener {
            startActivity(Intent(this,OutHisActivity::class.java))
        }
        binding.accountLayout.setOnClickListener {
            startActivity(Intent(this,AccountActivity::class.java))
        }
    }



    override fun initViewModel() {
        mainViewModel =
            ViewModelProvider(this, MyViewModelFactory.instance)[MainViewModel::class.java]
        mainViewModel.memberShowInfonResult.observe(this){
        }
        mainViewModel.carTotalResult.observe(this){
            binding.weightNum.text=it.totalWeight.toString()+"kg"
            binding.bagNum.text=it.totalNum.toString()+"包"
        }
    }

    override fun initData() {
        mainViewModel.getCartTotal()
    }


}