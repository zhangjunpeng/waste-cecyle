package com.dp.electronic_scale;

import com.dp.dp_serialportlist.Serialport_Factory;
import com.dp.dp_serialportlist.Serialport_Factory2;
import com.dp.dp_serialportlist.b;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button open_bt,test_bt,set_bt;
	EditText test_et;
	public static Serialport_Factory sf;//打印机串口
	public static Serialport_Factory2 sf2;//称的串口
	public static Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String apkRoot="chmod 777 "+getPackageCodePath();
		SystemManager.RootCommand(apkRoot);

		mHandler=new MyHandler();		
		sf=Serialport_Factory.getSerialport_Factory(this);
		sf2=Serialport_Factory2.getSerialport_Factory(this,mHandler);
		test_bt=(Button)findViewById(R.id.test_bt);
		open_bt=(Button)findViewById(R.id.open_bt);
		set_bt=(Button)findViewById(R.id.set_bt);
		test_et=(EditText)findViewById(R.id.test_et);
		open_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				sf2.OpenPort("ttyUSB10", "9600");
				sf.OpenPort("ttyS3", "115200");
			}
		});
		set_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//更多设置指令，请参考称的文档
				
				byte[] buf0="AT+AUTO=0\r\n".getBytes();//关闭自动获取重量数据，通过发送指令 AT+WEI 获取重量
				byte[] buf1="AT+AUTO=1\r\n".getBytes();//自动获取重量数据
				byte[] buf2="AT+WEI\r\n".getBytes();//手动获取重量
				byte[] buf3="AT+ZERO\r\n".getBytes();//称重清零
				byte[] buf4="AT+CAL=010000,002000,2,0".getBytes();//如 要 设 置 满 量 程 100kg0.01kg 的 秤 ， 且 有 20kg 的 校 准 砝 码 。 则 发 送 指 令
				byte[] buf5="AT+CAW".getBytes();//发送校准砝码指令，校准参数及零点校准成功后，在秤台上放上设置值所对应的砝码后发送该指令。校准完成。
				sf2.Sendbyte(buf1);
			}
		});
		test_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//打印测试
				sf.LabelBegin(576, 420);//先设置标签的宽度和高度	
				sf.LableText(100,15,2,0,"xxx第一人民医院");
				sf.LabelQRCode(90, 95,6,"xxxx医院信息");
				
				sf.LableText(330,90,1,0,"科室：内科门诊");
				sf.LableText(330,125,1,0,"医废收集人：张三丰");
				sf.LableText(330,160,1,0,"医废类型：感染性");
				sf.LableText(330,195,1,0,"重量："+weight+"Kg");
				sf.LableText(330,230,1,0,"登记日期：2019-04-29");				
				sf.LableText(330,265,1, 0, "登记时间：09:35:18");
				sf.LableText(330,310,1,0,"xxxx有限公司");
				sf.Labelend();//必须要有标签结束，否则不会打印
				sf.PaperCut();//切纸
				
			}
		});
	}
	float weight=0;
	 @SuppressLint("HandlerLeak")
	class MyHandler extends Handler {
			@Override
			public void handleMessage(Message msg) {			
				switch (msg.what) {			
				case Serialport_Factory2.BACKDATA: {
					String backdata=msg.getData().getString("backdata");
					String kg=backdata.substring(2, backdata.indexOf(","));
					weight=Float.parseFloat(kg);
					test_et.setText("称重："+weight+"Kg");
					break;
				}
			}
		}
	 }
}
