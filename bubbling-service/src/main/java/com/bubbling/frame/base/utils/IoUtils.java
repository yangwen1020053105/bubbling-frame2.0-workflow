package com.bubbling.frame.base.utils;

import java.io.*;

/**
 * @title: IoUtils
 * @Author yangwen
 * @Date: 2021-05-16 16:57
 * @ignore
 */
public class IoUtils {
    public static String inputStreamToString(InputStream inputStream) {
        StringBuffer buffer = new StringBuffer();
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
