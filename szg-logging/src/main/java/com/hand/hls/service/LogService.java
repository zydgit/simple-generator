package com.hand.hls.service;

import com.hand.hls.service.dto.LogQueryCriteria;
import com.hand.hls.domain.Log;
import com.hand.hls.service.dto.LogQueryCriteria;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;


public interface LogService {

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(LogQueryCriteria criteria, Pageable pageable);

    /**
     * queryAllByUser
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAllByUser(LogQueryCriteria criteria, Pageable pageable);

    /**
     * 新增日志
     * @param username
     * @param ip
     * @param joinPoint
     * @param log
     */
    @Async
    void save(String username, String ip, ProceedingJoinPoint joinPoint, Log log);

    /**
     * 查询异常详情
     * @param id
     * @return
     */
    Object findByErrDetail(Integer id);
}
