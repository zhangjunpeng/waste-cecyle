package com.zjp.base

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.zjp.receiver.BluetoothMonitorReceiver

open class BaseActivity : AppCompatActivity() {

    lateinit var receiver: BluetoothMonitorReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiver = BluetoothMonitorReceiver()
        val intentFilter = IntentFilter()
        intentFilter.let {
            it.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            it.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            it.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

        registerReceiver(receiver, intentFilter)
        initViewModel()
        setView()
        initData()
    }
    open fun setView(){

    }
    open fun initViewModel(){

    }

    open fun initData() {

    }

    open fun onRevicerScan(keyStr: String) {

    }

    var keyStr = StringBuffer()
    var count = 0

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        LogUtils.i(event.toString())
//        if (event!!.keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
//            return false
//        }
        count++
        if (event!!.unicodeChar == 10) {
            LogUtils.i(keyStr)
            LogUtils.i(count)
            onRevicerScan(keyStr.toString())
            keyStr = StringBuffer()
        } else {
            LogUtils.i(event.displayLabel)
            keyStr.append(event.displayLabel)
        }
        return super.onKeyUp(keyCode, event)
    }


    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

}