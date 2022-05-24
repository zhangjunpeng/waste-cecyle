package com.zjp.medicalwasterecycle.main

import android.content.Intent
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.SPUtils
import com.nextmar.requestdata.NameSpace
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.account.AccountActivity
import com.zjp.medicalwasterecycle.bagpack.BagPackActivity
import com.zjp.medicalwasterecycle.databinding.ActivityMainBinding
import com.zjp.medicalwasterecycle.outstock.OutHisActivity
import com.zjp.medicalwasterecycle.outstock.WasteOutActivity
import com.zjp.medicalwasterecycle.roomscan.RoomScanActivity
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
        binding.scanLayout.setOnClickListener {
            val intent1=Intent(this,RoomScanActivity::class.java)
            intent1.putExtra("code","KS100001100270")
            startActivity(intent1)
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
        mainViewModel.memberShowInfonResult.observe(this){
            if (it == null) return@observe
            SPUtils.getInstance().put(NameSpace.ProjectName,it.pname)
            SPUtils.getInstance().put(NameSpace.Phone,it.phone)
            SPUtils.getInstance().put(NameSpace.Name,it.name)

            setTitleContent(binding.title)
        }
    }

    override fun initData() {
        mainViewModel.memberShowInfo()
        mainViewModel.getCartTotal()
    }

    override fun onRevicerScan(keyStr: String) {
        val intent=Intent(this,RoomScanActivity::class.java)
        intent.putExtra("code",keyStr)
        startActivity(intent)
    }

}