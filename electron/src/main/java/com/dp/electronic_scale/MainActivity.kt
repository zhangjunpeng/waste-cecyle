package com.dp.electronic_scale

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.dp.dp_serialportlist.Serialport_Factory
import com.dp.dp_serialportlist.Serialport_Factory2
import com.dp.electronic_scale.databinding.ActivityMainBinding
import com.mc.enjoysdk.McSecure

class MainActivity : Activity() {

    lateinit var sf //打印机串口
            : Serialport_Factory
    lateinit var sf2 //称的串口
            : Serialport_Factory2
    var mHandler: Handler? = null

    val buf0 = "AT+AUTO=0\r\n".toByteArray() //关闭自动获取重量数据，通过发送指令 AT+WEI 获取重量
    val buf1 = "AT+AUTO=1\r\n".toByteArray() //自动获取重量数据
    val buf2 = "AT+WEI\r\n".toByteArray() //手动获取重量
    val buf3 = "AT+ZERO\r\n".toByteArray() //称重清零
    val buf4 =
        "AT+CAL=010000,002000,2,0".toByteArray() //如 要 设 置 满 量 程 100kg0.01kg 的 秤 ， 且 有 20kg 的 校 准 砝 码 。 则 发 送 指 令
    val buf5 = "AT+CAW".toByteArray() //发送校准砝码指令，校准参数及零点校准成功后，在秤台上放上设置值所对应的砝码后发送该指令。校准完成。
    val init = "AT\r\n".toByteArray()
    val version = "AT+VERSION?\r\n".toByteArray()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mcSecure = McSecure.getInstance(this)
        val ret = mcSecure.setSecurePasswd("Abc12345", "Abc12345")
        mcSecure.registSafeProgram("Abc12345")
        Log.d("enjoy", mcSecure.checkSafeProgramOfSelf().toString())
        mHandler = MyHandler()
        sf = Serialport_Factory.getSerialport_Factory(this)
        sf2 = Serialport_Factory2.getSerialport_Factory(this, mHandler)



        binding.openBt.setOnClickListener {
//            sf.OpenPort("ttyUSB2", "9600")
                sf2.OpenPort("ttyS2", "9600")


        }
        binding.setBt.setOnClickListener {
            //更多设置指令，请参考称的文档
            sf2.Sendbyte(buf1)

            //				sf2.Sendbyte(buf1);
//				sf2.Sendbyte(buf1);
        }
        binding.testBt.setOnClickListener {
            //打印测试
            sf.LabelBegin(576, 420) //先设置标签的宽度和高度	
            sf.LableText(100, 15, 2, 0, "xxx第一人民医院")
            sf.LabelQRCode(90, 95, 6, "xxxx医院信息")
            sf.LableText(330, 90, 1, 0, "科室：内科门诊")
            sf.LableText(330, 125, 1, 0, "医废收集人：张三丰")
            sf.LableText(330, 160, 1, 0, "医废类型：感染性")
            sf.LableText(330, 195, 1, 0, "重量：" + weight + "Kg")
            sf.LableText(330, 230, 1, 0, "登记日期：2019-04-29")
            sf.LableText(330, 265, 1, 0, "登记时间：09:35:18")
            sf.LableText(330, 310, 1, 0, "xxxx有限公司")
            sf.Labelend() //必须要有标签结束，否则不会打印
            sf.PaperCut() //切纸
        }
    }

    var weight = 0f

    @SuppressLint("HandlerLeak")
    internal inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
             Log.d("backdata", msg.toString())
            when (msg.what) {
                Serialport_Factory2.BACKDATA -> {

//					if (msg.obj != null) {
//						byte[] data = (byte[]) msg.obj;
//						StringBuffer dataStr = new StringBuffer();
//						for (byte data1 : data) {
//							dataStr.append(data1);
//							dataStr.append(",");
//						}
//						Log.e("backdata", dataStr.toString());
//					}
                    val bundle = msg.data
                    if (bundle.containsKey("backdata")) {
                        val backdata = bundle.getString("backdata")
                        Log.d("backdata", backdata.toString())
                        try {
                            val kg = backdata?.substring(2, backdata.indexOf(","))
                            weight = kg!!.toFloat()
                            binding.testEt.setText("称重：" + weight + "Kg")
                            Log.d("weight", "称重：" + weight + "Kg")
                        } catch (e: Exception) {
                        }
                    }
                }
            }
        }
    }


}