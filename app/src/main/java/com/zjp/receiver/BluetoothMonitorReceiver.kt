package com.zjp.receiver

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

class BluetoothMonitorReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val action: String? = intent.action
        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
        if (action != null) {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            when (action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val blueState: Int = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
                    when (blueState) {
                        BluetoothAdapter.STATE_ON -> Toast.makeText(
                            context,
                            "蓝牙已打开",
                            Toast.LENGTH_SHORT
                        ).show()
                        BluetoothAdapter.STATE_OFF -> Toast.makeText(
                            context,
                            "蓝牙已关闭",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                BluetoothDevice.ACTION_ACL_CONNECTED -> Toast.makeText(
                    context,
                    "蓝牙设备:" + device!!.name.toString() + "已连接",
                    Toast.LENGTH_SHORT
                ).show()
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> Toast.makeText(
                    context,
                    "蓝牙设备:" + device!!.name.toString() + "已断开",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}