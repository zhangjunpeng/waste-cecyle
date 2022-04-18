package com.zjp.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Handler
import android.os.Message
import com.blankj.utilcode.util.LogUtils
import com.hnj.dp_nusblist.USBFactory

class USBConnectUtil {
    var usbfactory: USBFactory? = null
    private var mUsbManager: UsbManager? = null
    private var deviceList: HashMap<String, UsbDevice>? = null
    private var deviceIterator: Iterator<UsbDevice>? = null

    companion object {
        val instance: USBConnectUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            USBConnectUtil()
        }

        val mHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {

            }
        }
    }


    //搜索USB设备search USB
    @SuppressLint("RtlHardcoded")
    private fun SearchUSB(context: Context) {

        usbfactory = USBFactory.getUsbFactory(mHandler)
        mUsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        deviceList = mUsbManager?.deviceList
        deviceIterator = deviceList?.values?.iterator()
        usbfactory?.CloseUSB()
        if (deviceList?.size!! > 0) {
            // 初始化选择对话框布局，并添加按钮和事件
            while (deviceIterator?.hasNext() == true) { // 这里是if不是while，说明我只想支持一种device
                val device: UsbDevice = deviceIterator?.next()!!
                if (device.manufacturerName == "DP_PRINTER") {
                    device.interfaceCount
                    LogUtils.e("tInterfaceCount==" + device.interfaceCount)
                    val mPermissionIntent = PendingIntent.getBroadcast(
                        context,
                        0,
                        Intent(context.getApplicationInfo().packageName),
                        0
                    )
                    if (!mUsbManager!!.hasPermission(device)) {
                        mUsbManager!!.requestPermission(device, mPermissionIntent)
                    } else {
                        val connectUsb: Boolean =
                            usbfactory!!.connectUsb(
                                mUsbManager,
                                device
                            )
                        if (connectUsb) {
                            LogUtils.e("USB连接成功!!")
                            return
                        } else {
                            LogUtils.e("USB连接失败!!")
                        }

                    }
                }
            }
        }
    }

    fun haveScaleConnected(): Boolean {
        var rtn = false
        if (usbfactory != null) {
            rtn = usbfactory!!.getconnectstate()
        }
        return rtn
    }
}