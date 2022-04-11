package com.zjp.test.test;

/**
 * Created by Android Studio.
 * User: liusong
 * Date: 2020/7/14
 * Time: 17:17
 */
public class PrinterProxy {
//    private static PrinterProxy sPrinterProxy;
//    private Serialport_Factory mPrintFactory;//打印机串口
//    private boolean mConnectedFlag = false;
//
//    public PrinterProxy(Context context, Handler handle) {
//        mPrintFactory = Serialport_Factory.getSerialport_Factory(context, handle);
//    }
//
//    public static synchronized PrinterProxy getInstance(Context context, Handler handle) {
//        if (sPrinterProxy == null) {
//            synchronized ((PrinterProxy.class)) {
//                if (sPrinterProxy == null) {
//                    sPrinterProxy = new PrinterProxy(context, handle);
//                }
//            }
//        }
//        return sPrinterProxy;
//    }
//
//    public boolean OpenPort(String portNAME, String baudrate) {
//        return mPrintFactory.OpenPort(portNAME, baudrate);
//    }
//
//    public void checkPaper() {
//        Serialport_Factory.Check_Paper();
//    }
//
//    public boolean isConnected() {
//        try {
//            return mPrintFactory.isConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public void setConnectedFlag(boolean connectedFlag) {
//        mConnectedFlag = connectedFlag;
//    }
//
//    public boolean getConnectedFlag() {
//        return mConnectedFlag;
//    }
//
//    public Serialport_Factory getPrintFactory() {
//        return mPrintFactory;
//    }
}
