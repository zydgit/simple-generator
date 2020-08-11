package com.hand.hls.service.dto;

import com.hand.hls.annotation.Query;
import lombok.Data;
import com.hand.hls.annotation.Query;

/**
 * 日志查询类
 */
@Data
public class LogQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query
    private String logType;

    @Query(type = Query.Type.INNER_LIKE)
    private String description;

    @Query
    private String requestIp;
}
