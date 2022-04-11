package com.zjp.test.test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Android Studio.
 * User: liusong
 * Date: 2020/11/18
 * Time: 18:21
 */
public class Util {
    public static String getBytesString(byte[] data) {
        if(data ==null || data.length <1){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte tmp : data) {
            sb.append(" " + Integer.toHexString(((0xff) & tmp)));
        }
        return sb.toString();
    }

    /***
     * 从输入流获取字节数组,当文件很大时，报java.lang.OutOfMemoryError: Java heap space
     *
     * @since 2014-02-19
     * @param inputStream
     * @throws IOException

     */
    public static byte[] readBytesFromInputStream(InputStream inputStream
    ) throws IOException {
        int readSize;
        byte[] bytes = null;
        bytes = new byte[100];
        long length_tmp = 100;
        long index = 0;// start from zero
        while ((readSize = inputStream.read(bytes, (int) index, (int) length_tmp)) != -1) {
            length_tmp -= readSize;
            if (length_tmp == 0) {
                break;
            }
            index = index + readSize;
        }
        return bytes;
    }
}
