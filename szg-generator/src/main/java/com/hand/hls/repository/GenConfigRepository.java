package com.hand.hls.repository;

import com.hand.hls.domain.GenConfig;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenConfigRepository extends JpaRepository<GenConfig,Integer> {

    /**
     * 查询表配置
     * @param tableName 表名
     * @return /
     */
    GenConfig findByTableName(String tableName);
}
