package com.hand.hls.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具
 */
public class ThrowableUtils {

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
