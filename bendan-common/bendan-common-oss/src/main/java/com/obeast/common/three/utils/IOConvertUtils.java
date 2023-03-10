package com.obeast.common.three.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wxl
 * Date 2022/12/26 21:00
 * @version 1.0
 * Description: IO转换工具类
 */
public class IOConvertUtils {

    private IOConvertUtils() {
    }

    /**
     * inputStream转outputStream
     * **/
    public static ByteArrayOutputStream iConvertO(InputStream in) throws Exception {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    /**
     * outputStream转inputStream
     * **/
    public static ByteArrayInputStream oConvertI(OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * inputStream转String
     * **/
    public static String iConvertString(InputStream in) throws Exception {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream.toString();
    }

    /**
     * OutputStream 转String
     * **/
    public static String oToString(OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream.toString();
    }

    /**
     * String转inputStream
     * **/
    public static ByteArrayInputStream stringConvertI(String in) throws Exception {
        return new ByteArrayInputStream(in.getBytes());
    }

    /**
     * String 转outputStream
     * **/
    public static ByteArrayOutputStream parse_outputStream(String in) throws Exception {
        return iConvertO(stringConvertI(in));
    }
}
