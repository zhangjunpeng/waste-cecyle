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

    override fun setView() {
        binding=ActivityRoomScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    override fun initData() {
        val code=intent.getStringExtra("code")
        if (code!=null){
            DialogUtil.instance.showProgressDialog(this)
            roomScanViewModel.getRoomInfo(code)
        }
    }
}