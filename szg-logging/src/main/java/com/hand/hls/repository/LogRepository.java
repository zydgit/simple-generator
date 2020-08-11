package com.hand.hls.repository;

import com.hand.hls.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LogRepository extends JpaRepository<Log,Integer>, JpaSpecificationExecutor {

    /**
     * 获取一个时间段的IP记录
     * @param date1
     * @param date2
     * @return
     */
    @Query(value = "select count(*) FROM (select request_ip FROM fst_log where create_time between to_date(?1, 'yyyy-mm-dd') and to_date(?2, 'yyyy-mm-dd') GROUP BY request_ip)",nativeQuery = true)
    Integer findIp(String date1, String date2);

    /**
     * findExceptionById
     * @param id
     * @return
     */
    @Query(value = "select exception_detail FROM fst_log where id = ?1",nativeQuery = true)
    String findExceptionById(Integer id);
}
