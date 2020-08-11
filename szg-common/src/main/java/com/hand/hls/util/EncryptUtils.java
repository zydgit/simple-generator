package com.hand.hls.util;

import org.springframework.util.DigestUtils;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加密
 */
public class EncryptUtils {

    private static String strKey = "Passw0rd";

    /**
     * 对称加密
     * @param source
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String source) throws Exception {
        if (source == null || source.length() == 0){
            return null;
        }

        // key转换
        DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return byte2hex(
                cipher.doFinal(source.getBytes("UTF-8"))).toUpperCase();
    }

    public static String byte2hex(byte[] inStr) {
        String stmp;
        StringBuffer out = new StringBuffer(inStr.length * 2);
        for (int n = 0; n < inStr.length; n++) {
            stmp = Integer.toHexString(inStr[n] & 0xFF);
            if (stmp.length() == 1) {
                // 如果是0至F的单位字符串，则添加0
                out.append("0" + stmp);
            } else {
                out.append(stmp);
            }
        }
        return out.toString();
    }


    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0){
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 对称解密
     * @param source
     * @return
     * @throws Exception
     */
    public static String desDecrypt(String source) throws Exception {
        if (source == null || source.length() == 0){
            return null;
        }

        // key转换
        DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 解密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] src = hex2byte(source.getBytes("UTF-8"));
        byte[] retByte = cipher.doFinal(src);
        return new String(retByte, "UTF-8");
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        return  DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
