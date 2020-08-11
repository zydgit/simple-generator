package com.hand.hls.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
public class LogSmallDTO implements Serializable {

    /**
     * 描述
     */
    private String description;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 创建日期
     */
    private Timestamp createTime;
}
