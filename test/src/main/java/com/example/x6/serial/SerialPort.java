package com.example.x6.serial;

/**
 * Created by X6 on 2017/5/4.
 */

import android.util.Log;

import com.zjp.test.test.Util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
     !!!工控机串口通讯类SerialPort,路径一定要是 com.example.x6.serial 否则会出现异常。
 */
public class SerialPort {
    private static SerialPort mSerialPort;
    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private Thread mThread;

    public void setDataListener(GetDataListener dataListener) {
        mDataListener = dataListener;
    }

    private byte[] buffer = new byte[50];// 缓冲区字节数组，信息不能大于此缓冲区
    private GetDataListener mDataListener;

    public static interface GetDataListener {
        void getData(byte[] data);

    }

    public static SerialPort getInstance() {
        if (mSerialPort == null) {
            synchronized (SerialPort.class) {
                if (mSerialPort == null) {
                    mSerialPort = new SerialPort();
                }
            }
        }
        return mSerialPort;
    }

    private SerialPort() {
        receiveThread();
    }

    /*
     * 接收串口返回的数据包
     * Reverse:July-04-2019
     */
    private void receiveThread() {
        // 接收
        mThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    int size = 0;
                    try {
                        //*****************************这是修改后的**********************************
                        if (mInputStream != null) {
                            if (mInputStream.available() > 0 == false) {
                                continue;
                            }
                            int count = 0;
                            while (count == 0) {
                                count = mInputStream.available();
                            }
                            byte[] data = new byte[count];
                            size = mInputStream.read(data);
                            if (size > 0) {
                                Log.i(TAG, "接收到串口信息:" + Util.getBytesString(data));
                                if (mDataListener != null) {
                                    mDataListener.getData(data);
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "接收失败。。。");
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread.start();
    }


    public void write(byte[] buffer) {
        try {
            byte[] sendData = buffer;
            if (sendData != null && sendData.length > 2) {
                int sum = 0;
                for (int i = 0; i < (sendData.length - 2); i++) {
                    sum = sum + sendData[i];
                }
                sendData[sendData.length - 2] = (byte) (sum & 127);
            }
            if (mOutputStream != null) {
                mOutputStream.write(sendData);
                mOutputStream.flush();
                Log.i(TAG, "TcpSocketData,Send:" + getBytesString(sendData));
            } else {
                Log.i(TAG, "TcpSocketData,Send failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getBytesString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte tmp : data) {
            sb.append(" " + Integer.toHexString(((0xff) & tmp)));
        }
        return sb.toString();
    }

    public void open(File device, int baudrate, int flags) throws SecurityException, IOException {

//        检查访问权限，如果没有读写权限，进行文件操作，修改文件访问权限
        if (!device.canRead() || !device.canWrite()) {
            try {
                //通过挂在到linux的方式，修改文件的操作权限
                Process su = Runtime.getRuntime().exec("/system/xbin/su");
                //一般的都是/system/bin/su路径，有的也是/system/xbin/su
                String cmd = "chmod 777 " + device.getAbsolutePath() + "\n" + "exit\n";
                Log.e("cmd :", cmd);
                su.getOutputStream().write(cmd.getBytes());

                if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        if (mFd != null) {
            close();
        }
        mFd = open(device.getAbsolutePath(), baudrate, flags);

        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        } else {
            Log.e(TAG, " open success");
        }

        mInputStream = new FileInputStream(mFd);
        mOutputStream = new FileOutputStream(mFd);

    }

    // Getters and setters
    public InputStream getInputStream() {
        return mInputStream;
    }

    public OutputStream getOutputStream() {
        return mOutputStream;
    }

    // JNI(调用java本地接口，实现串口的打开和关闭)

    /**
     * @param path     串口设备的据对路径
     * @param baudrate 波特率
     * @param flags    校验位
     */
    private native static FileDescriptor open(String path, int baudrate, int flags);

    public native void close();

    static {//加载jni下的C文件库
        System.loadLibrary("serial_port");
    }
}