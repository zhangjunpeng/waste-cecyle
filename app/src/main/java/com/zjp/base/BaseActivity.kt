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

        setView()


    }
    open fun setView(){

    }
    open fun initViewModel(){

    }

    open fun onRevicerScan(){

    }


    var keyStr=""
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
//        LogUtils.i(keyCode.toString()+"==="+event!!.unicodeChar.toString())
        if (event!!.unicodeChar==10){
            LogUtils.i(keyStr)
            keyStr=""
        }else{
            keyStr+=event.displayLabel

        }
        return true
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

}