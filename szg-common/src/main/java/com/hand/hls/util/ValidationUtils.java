package com.hand.hls.util;

import com.hand.hls.exception.BadRequestException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具
 */
public class ValidationUtils {

    /**
     * 验证空
     * @param optional
     */
    public static void isNull(Optional optional, String entity,String parameter , Object value){
        if(!optional.isPresent()){
            String msg = entity
                         + " 不存在 "
                         +"{ "+ parameter +":"+ value.toString() +" }";
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return string.matches(regEx1);
    }

    /**
     * 验证是否手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 验证是否选择数据
     * @param ids
     */
    public static void isSelected(Integer [] ids) {
        if (ids == null || ids.length == 0) {
            throw new BadRequestException("请选择数据后在操作！");
        }
    }
}
