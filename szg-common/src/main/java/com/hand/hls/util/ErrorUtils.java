package com.hand.hls.util;

public class ErrorUtils {


    public static String getAllExceptionMessage(Exception ex) {
        String sOut = "";
        sOut += ex.getMessage() + "\r\n";
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
    }
}
