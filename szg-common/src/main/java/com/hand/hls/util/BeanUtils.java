package com.hand.hls.util;

import java.io.*;
import java.util.List;

/**
 * @author zy
 * @version 1.0
 * @date 2020/4/8 1:31 PM
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <T> List<T> deepCopy(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
