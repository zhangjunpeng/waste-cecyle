package com.zjp.medicalwasterecycle.roomscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivityRoomScanBinding
import com.zjp.medicalwasterecycle.main.MainViewModel
import com.zjp.utils.DialogUtil
import com.zjp.viewmodel.MyViewModelFactory

class RoomScanActivity : BaseActivity() {

    lateinit var binding: ActivityRoomScanBinding
    lateinit var roomScanViewModel: RoomScanViewModel

    val preNum="包裹编号："
    val preRoom1="科室："
    val preRoom2="所属科室："
    val preNurse="交接护士："
    val preCate="医废类型："


    val bagStatus=HashMap<String,String>().apply {
        this["unbroken"]="1"
        this["sterile"]="0"
        this["tight"]="0"
        this["classified"]="0"
        this["commodious"]="0"
        this["few_medical"]="1"
    }

    override fun setView() {
        binding=ActivityRoomScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitleContent(binding.title)
    }

    override fun initViewModel() {
        roomScanViewModel= ViewModelProvider(this, MyViewModelFactory.instance)[RoomScanViewModel::class.java]
        roomScanViewModel.roomShowDataResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()

        }
        roomScanViewModel.errorResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()

        }
    }

    override fun onRevicerScan(keyStr: String) {

    }

    override fun initData() {
        val code=intent.getStringExtra("code")
        if (code!=null){
            DialogUtil.instance.showProgressDialog(this)
            roomScanViewModel.getRoomInfo(code)
            roomScanViewModel.getRoomBagList(code)
        }
    }
}