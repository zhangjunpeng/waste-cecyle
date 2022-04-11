package com.zjp.test.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.x6.serial.SerialPort;

import com.hnj.dp_nusblist.USBFactory;
import com.zjp.test.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, SerialPort.GetDataListener {
    private final static int MSG_WEIGHT_DATA = 1;
    private final static int MSG_SUCCESS_TOAST = 2;
    private final static int MSG_FAILURE_TOAST = 3;
    private final static int MSG_SHOW_TIPS = 4;
    private final static int MSG_READ_SERIES = 300;
    private static final int MSG_CONNECTED_AUTO = 500;
    private static final int MSG_CHECK_CONNECTED_RESULT = 0x05;
    private int mDeleayTime = 500;
    private UsbManager mUsbManager;
    private HashMap<String, UsbDevice> deviceList;
    private Iterator<UsbDevice> deviceIterator;
    public static USBFactory usbfactory;
    private String TAG = "MainActivity";
    private Context mContext;
    private SerialPort mSerialPort;
    private PrinterProxy mPrinterpProxy;
    private byte[] read_weight_current = new byte[]{(byte) 0x11, (byte) 0x42, (byte) 0x3f, (byte) 0x12, (byte) 0x0d};
    private byte[] read_weight_stable = new byte[]{(byte) 0x11, (byte) 0x43, (byte) 0x3f, (byte) 0x13, (byte) 0x0d};
    private byte[] read_weight_max = new byte[]{(byte) 0x11, (byte) 0x50, (byte) 0x3f, (byte) 0x20, (byte) 0x0d};
    private byte[] zero_weight = new byte[]{(byte) 0x11, (byte) 0x52, (byte) 0x40, (byte) 0x23, (byte) 0x0D};
    private byte[] fix_weight_max = new byte[]{(byte) 0x11, (byte) 0x4f, (byte) 0x7e, (byte) 0x3c, (byte) 0x36, (byte) 0x32, (byte) 0x30, (byte) 0x00, (byte) 0x0D};
    private byte[] fix_weight_zero = new byte[]{(byte) 0x11, (byte) 0x4f, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x00, (byte) 0x00, (byte) 0x0D};
    private byte[] set_weight_max = new byte[]{(byte) 0x11, (byte) 0x50, (byte) 0x7e, (byte) 0x3c, (byte) 0x36, (byte) 0x32, (byte) 0x30, (byte) 0x00, (byte) 0x0D};
    private byte[] reset_all = new byte[]{(byte) 0x11, (byte) 0x54, (byte) 0x41, (byte) 0x26, (byte) 0x0D};

    private byte[] read_weight_fendu = new byte[]{(byte) 0x11, (byte) 0x4B, (byte) 0x3f, (byte) 0x1B, (byte) 0x0D};
    private byte[] set_weight_fendu = new byte[]{(byte) 0x11, (byte) 0x4B, (byte) 0x42, (byte) 0x1B, (byte) 0x0D};
    private boolean mReadSeriesFlag = false;
    private int mFendu = 1;
    private int mTempFendu = -1;
    private int mReadTimes = 0;
    private TextView mTextView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CONNECTED_AUTO:
                    SearchUSB();
                    break;
                case MSG_SHOW_TIPS:
                    String tips = (String) msg.obj;
                    Toast.makeText(mContext, tips, Toast.LENGTH_LONG).show();
                    break;
                case MSG_WEIGHT_DATA:
                    String data = (String) msg.obj;
                    mTextView.setText((String) msg.obj + "KG");
                    break;
                case MSG_SUCCESS_TOAST:
                    Toast.makeText(mContext, "操作成功！", Toast.LENGTH_LONG).show();
                    break;
                case MSG_FAILURE_TOAST:
                    Toast.makeText(mContext, "操作失败！", Toast.LENGTH_LONG).show();
                    break;
                case MSG_CHECK_CONNECTED_RESULT:
                    if (usbfactory != null) {
                        if (usbfactory.getconnectstate()) {
                            Toast.makeText(mContext, "打印机连接成功", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "打印机连接成功 ");
                            removeMessages(MSG_CHECK_CONNECTED_RESULT);
                        } else {
//                        removeMessages(MSG_CONNECTED_AUTO);
//                        sendEmptyMessageDelayed(MSG_CONNECTED_AUTO, 3000);
                            Log.e(TAG, "==============连接失败=======000");
                        }
                    }
                    break;
                case USBFactory.CHECKPAGE_RESULT:
                    if (msg.getData().getString("state").equals("1")) {
                        Log.e(TAG, "==============打印机有纸=======000");
                    } else {
                        Toast.makeText(mContext, "打印机缺纸", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "==============打印机没有纸=======000");
                    }
                    break;
                case USBFactory.PRINTSTATE:
                    boolean printstate = msg.getData().getBoolean("printstate");
                    if (printstate) {
                        Toast.makeText(mContext, "打印完成", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "==============打印完成=======000");
                    } else {
                        Toast.makeText(mContext, "打印失败", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "==============打印失败=======000");
                    }
                    break;
                case MSG_READ_SERIES:
                    Log.d(TAG, "read Times:" + (mReadTimes++));
                    mSerialPort.write(read_weight_current);
                    break;
                default:
                    break;
            }
        }
    };
    private RadioGroup mRadioGroup;
    private boolean mClearWeightFlag = false;
    private Spinner mWeightSp;
    private int mCurrentCorrectValues = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mTextView = findViewById(R.id.current_weight_tv);
        mSerialPort = SerialPort.getInstance();
        mSerialPort.setDataListener(this);
//        mPrinterpProxy = PrinterProxy.getInstance(this, mHandler);
        mRadioGroup = findViewById(R.id.clear_radio_group);
        mWeightSp = findViewById(R.id.electronic_correct_fama_sp);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.clear_yes) {
                    mSerialPort.write(fix_weight_zero);
                } else {
                    mClearWeightFlag = false;
                }
            }
        });
        List<String> mCorrecctStr = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            if (i == 0) {
                mCorrecctStr.add(1 + " KG");
            } else {
                mCorrecctStr.add((i * 5) + " KG");
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, mCorrecctStr);
        adapter.setDropDownViewResource(R.layout.dropdown_stytle);
        mWeightSp.setAdapter(adapter);
        mWeightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mCurrentCorrectValues = 1;
                } else {
                    mCurrentCorrectValues = position * 5;
                }
                mCurrentCorrectValues = mCurrentCorrectValues * 100;
                Log.i(TAG, "量程:" + mCurrentCorrectValues);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        usbfactory=USBFactory.getUsbFactory(mHandler);
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mHandler.sendEmptyMessageDelayed(MSG_CONNECTED_AUTO, 2000);
    }

    @Override
    public void getData(byte[] data) {
        if (data != null && data.length > 3) {
            byte base = 0x30;
            switch (data[1]) {
                case 0x41://读取即时重量内码
                case 0x42://读取即时重量
                case 0x43://读取稳定重量
                    if (mReadSeriesFlag) {
                        mHandler.sendEmptyMessageDelayed(MSG_READ_SERIES, mDeleayTime);
                    }
                    if (data.length != 10) {
                        Log.i(TAG, "接收数据长度异常。");
                        return;
                    }
                    int x0 = (data[7] - base);//小数点位数
                    int x5 = (data[6] - base) * 65536;
                    int x4 = (data[5] - base) * 4096;
                    int x3 = (data[4] - base) * 256;
                    int x2 = (data[3] - base) * 16;
                    int x1 = (data[2] - base) * 1;
                    Log.i(TAG, "分重量：" + "t5=" + x5 + ",t4=" + x4 + ",t3=" + x3 + ",t2=" + x2 + ",t1=" + x1);
                    Log.i(TAG, "小数位:" + (x0 & 3) + ",fendu:" + mFendu);
                    double wieht = x5 + x4 + x3 + x2 + x1;
                    wieht = (wieht) * 0.01 + mFendu;
                    Log.i(TAG, "总重量=" + wieht);
                    Message message = mHandler.obtainMessage(MSG_WEIGHT_DATA);
                    message.obj = String.format("%.2f", wieht) + "";
                    mHandler.sendMessage(message);
                    break;
                case 0x50:///读取满量程
                    if (data.length == 5) {
                        switch (data[2]) {
                            case 0x41:
                                Log.i(TAG, "设置满量程成功，即将设置标定值...");
                                message = mHandler.obtainMessage(MSG_WEIGHT_DATA);
                                message.obj = "设置满量程成功，即将设置标定值...";
                                mHandler.sendMessage(message);
                                mSerialPort.write(getFixedWeightArray(mCurrentCorrectValues, fix_weight_max));
                                break;
                            case 0x42:
                                Log.i(TAG, "设置满量程值太小。");
                            case 0x43:
                                Log.i(TAG, "设置满量程值太大。");
                                Toast.makeText(this, "满量程设置失败，请进行恢复出厂设置后再使用！！！！", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return;
                    } else if (data.length == 9) {
                        x5 = (data[6] - base) * 65536;
                        x4 = (data[5] - base) * 4096;
                        x3 = (data[4] - base) * 256;
                        x2 = (data[3] - base) * 16;
                        x1 = (data[2] - base) * 1;
                        Log.i(TAG, "分重量：" + "t1=" + x5 + ",t2=" + x4 + ",t3=" + x3 + ",t4=" + x2 + ",t5=" + x1);
                        wieht = x5 + x4 + x3 + x2 + x1;
                        wieht = (wieht) * 0.01;
                        Log.i(TAG, "总重量=" + wieht);
                        message = mHandler.obtainMessage(MSG_WEIGHT_DATA);
                        message.obj = wieht + "";
                        mHandler.sendMessage(message);
                    }
                    break;
                case 0x52:
                    switch (data[2]) {
                        case 0x41:
                            Log.i(TAG, "置零成功.");
                            break;
                        case 0x42:
                            Log.i(TAG, "置零超过设定范围");
                            break;
                        case 0x43:
                            Log.i(TAG, "置零时不稳定.");
                            break;
                    }
                    break;
                case 0x46:
                    break;
                case 0x47:
                    break;
                case 0x48:
                    break;
                case 0x49:
                    break;
                case 0x4a:
                    switch (data[2]) {
                        case 0x40:
                            Log.i(TAG, "0位小数点.");
                            break;
                        case 0x41:
                            Log.i(TAG, "1位小数点");
                            break;
                        case 0x42:
                            Log.i(TAG, "2位小数点.");
                            break;
                        case 0x43:
                            Log.i(TAG, "3位小数点.");
                            break;
                    }
                    break;
                case 0x4b:
                    switch (data[2]) {
                        case 0x41:
                            if (mTempFendu != -1) {//设置分度值
                                mFendu = mTempFendu;
                                message = mHandler.obtainMessage(MSG_SHOW_TIPS);
                                message.obj = "分度值设置成功：" + mFendu;
                                mHandler.sendMessage(message);
                                Log.i(TAG, "分度值设置成功：" + mFendu);
                            } else {  //读取分度值
                                mFendu = 1;
                                Log.i(TAG, "分度值1");
                            }
                            break;
                        case 0x42:
                            mFendu = 2;
                            Log.i(TAG, "分度值2.");
                            break;
                        case 0x43:
                            mFendu = 5;
                            Log.i(TAG, "分度值5.");
                            break;
                        case 0x44:
                            mFendu = 10;
                            Log.i(TAG, "分度值10.");
                            break;
                        case 0x45:
                            mFendu = 20;
                            Log.i(TAG, "分度值20.");
                            break;
                    }
                    mTempFendu = -1;
                    break;
                case 0x4f:
                    switch (data[2]) {
                        case 0x41:
                            Log.i(TAG, "标定成功");
                            mHandler.sendEmptyMessageDelayed(MSG_SUCCESS_TOAST, 1000);
                            mClearWeightFlag = true;
                            break;
                        case 0x42:
                            Log.i(TAG, "重量加载方向反了");
                            message = mHandler.obtainMessage(MSG_WEIGHT_DATA);
                            message.obj = "重量加载方向反了";
                            mHandler.sendMessage(message);
                            mHandler.sendEmptyMessageDelayed(MSG_FAILURE_TOAST, 1000);
                            break;
                        case 0x43:
                            Log.i(TAG, "标定失败，未检测到明显的重量加载！");
                            message = mHandler.obtainMessage(MSG_WEIGHT_DATA);
                            message.obj = "标定失败，未检测到明显的重量加载！";
                            mHandler.sendMessage(message);
                            mHandler.sendEmptyMessageDelayed(MSG_FAILURE_TOAST, 1000);
                            break;
                        case 0x44:
                            Log.i(TAG, "标定时称重不稳定.");
                            message = mHandler.obtainMessage(MSG_WEIGHT_DATA);
                            message.obj = "标定时称重不稳定.";
                            mHandler.sendMessage(message);
                            mHandler.sendEmptyMessageDelayed(MSG_FAILURE_TOAST, 1000);
                            break;
                    }
                    break;

            }
        }

    }

    @Override
    public void onClick(View v) {
        mReadSeriesFlag = false;
        mTempFendu = -1;
        try {
            switch (v.getId()) {
                case R.id.open_printer_devices:
//                    if (!mPrinterpProxy.isConnected()) {
//                        boolean rtn = mPrinterpProxy.OpenPort("ttyS2", "115200");
//                        Log.d(TAG, "open printer:" + rtn);
//                    } else {
//                        Log.d(TAG, "printer have open.");
//                    }
                    break;
                case R.id.check_printer_paper:
                    Log.d(TAG, "checkPaper()");
//                    mPrinterpProxy.checkPaper();
                    usbfactory.Check_Paper();
                    break;
                case R.id.printer_qrc:
                    /**
                     * LabelQRCode 标签二维码打印
                     * 第一个参数  X轴横向位移量
                     * 第二个参数  Y轴纵向位移量
                     * 第三个参数  版本号1-18，数字越大二维码越密集，二维码呈现内容就越多。
                     * 第四个参数   二维码大小 有效值2-8
                     *  第5个参数   二维码文本内容
                     */
                    if (haveScaleConnected()) {
                        usbfactory.LabelBegin(560, 380);//先设置标签的宽度和高度
                        usbfactory.LabelQRCode(70, 70, 14, 4, "abc123");
                        usbfactory.Labelend();//必须要有标签结束，否则不会打印
                        usbfactory.PaperCut();
                    }
                    break;
                case R.id.printer_text:
                    /**
                     * LableText 标签文本打印
                     * 第一个参数  X轴横向位移量
                     * 第二个参数  Y轴纵向位移量
                     * 第三个参数  字体大小 有效值1-6
                     * 第四个参数   是否旋转 0不旋转，1旋转90°
                     * 第五个参数   文本加粗0不加粗，1加粗字体
                     * 第六个参数   文本内容
                     */
                    usbfactory.LabelBegin(560, 380);///先设置标签的宽度和高度
                    usbfactory.LableText(90, 20, 1, 0, "1正常字体大小");
                    usbfactory.LableText(90, 50, 2, 0, "2字体放大2倍");
                    usbfactory.Labelend();//必须要有标签结束，否则不会打印
                    usbfactory.PaperCut();
                    break;
                case R.id.printer_text_and_qrc:
                    usbfactory.LabelBegin(560, 380);
                    usbfactory.LabelQRCode(70, 70, 14, 3, "abc123");
                    usbfactory.LableText(80, 20, 2, 0, 0, "深圳市第二高级中学");
                    usbfactory.LableText(300, 80, 2, 0, 0, "路人甲");
                    usbfactory.Labelend();//必须要有标签结束，否则不会打印
                    usbfactory.PaperCut();
                    break;
                case R.id.printer_setmode_biaoqian:
                    usbfactory.setprintmode(1);
                    break;
                case R.id.printer_setmode_xiaopiao:
                    usbfactory.setprintmode(2);
                    break;
                case R.id.open_weight_devices:
                    mSerialPort.open(new File("/dev/ttyS3"), 19200, 0);
                    break;
                case R.id.get_weight_current:
                    mSerialPort.write(read_weight_current);
                    break;
                case R.id.get_weight_current_stable:
                    mSerialPort.write(read_weight_stable);
                    break;
                case R.id.get_weight_max:
                    mSerialPort.write(read_weight_max);
                    break;
                case R.id.set_weight_zero:
                    mSerialPort.write(zero_weight);
                    break;
                case R.id.read_weight_series:
                    mReadSeriesFlag = true;
                    mReadTimes = 0;
                    mHandler.sendEmptyMessage(MSG_READ_SERIES);
                    break;
                case R.id.read_weight_fendu:
                    mSerialPort.write(read_weight_fendu);
                    break;
                case R.id.set_weight_fendu:
                    mTempFendu = 2;
                    mSerialPort.write(getFixedFenduArray(mTempFendu));
                    break;
                case R.id.fix_weight:
                    if (!mClearWeightFlag || mCurrentCorrectValues == -1) {
                        Toast.makeText(this, "请按照以上步骤逐步执行.", Toast.LENGTH_LONG).show();
                    } else {
                        mSerialPort.write(getFixedWeightArray(mCurrentCorrectValues, set_weight_max));
                    }
                    break;
                case R.id.reset_all:
                    mSerialPort.write(reset_all);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getFixedWeightArray(int weight, byte[] base) {
        for (int i = 0; i < 5; i++) {
            base[2 + i] = (byte) (((weight >> (i * 4)) & 0xf) + 0x30);
        }
        return base;
    }

    private byte[] getFixedFenduArray(int fendu) {
        switch (fendu) {
            case 1:
                set_weight_fendu[2] = 0x41;
                break;
            case 2:
                set_weight_fendu[2] = 0x42;
                break;
            case 5:
                set_weight_fendu[2] = 0x43;
                break;
            case 10:
                set_weight_fendu[2] = 0x44;
                break;
            case 20:
                set_weight_fendu[2] = 0x42;
                break;
        }
        return set_weight_fendu;
    }

    //搜索USB设备search USB
    @SuppressLint("RtlHardcoded")
    private void SearchUSB() {
        deviceList = mUsbManager.getDeviceList();
        deviceIterator = deviceList.values().iterator();
        usbfactory.CloseUSB();
        if (deviceList.size() > 0) {
            // 初始化选择对话框布局，并添加按钮和事件
            while (deviceIterator.hasNext()) { // 这里是if不是while，说明我只想支持一种device
                final UsbDevice device = deviceIterator.next();
                device.getInterfaceCount();
                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(mContext.getApplicationInfo().packageName), 0);
                if (!mUsbManager.hasPermission(device)) {
                    mUsbManager.requestPermission(device, mPermissionIntent);
                } else {
                    boolean connectUsb = usbfactory.connectUsb(mUsbManager, device);
                    if (connectUsb) {
                        Log.e(TAG, "USB连接成功!!");
                        mHandler.removeMessages(MSG_CHECK_CONNECTED_RESULT);
                        return;
                    } else {
                        Log.e(TAG, "USB连接失败!!");
                    }
                    mHandler.removeMessages(MSG_CHECK_CONNECTED_RESULT);
                    mHandler.sendEmptyMessageDelayed(MSG_CHECK_CONNECTED_RESULT, 2000);
                }
            }
        }
    }

    public boolean haveScaleConnected() {
        boolean rtn = false;
        if (usbfactory != null) {
            rtn = usbfactory.getconnectstate();
        }
        return rtn;
    }
}
