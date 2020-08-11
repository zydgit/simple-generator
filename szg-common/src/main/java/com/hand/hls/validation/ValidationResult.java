package com.hand.hls.validation;

import lombok.Data;

import java.util.Map;

/**
 * @author zy
 * @version 1.0
 * @date 2020/3/30 2:34 PM
 */

@Data
public class ValidationResult {

    /** 校验结果是否有错 **/
    private boolean hasErrors;

    /** 校验错误信息 **/
    private Map<String, String> errorMsg;
}
