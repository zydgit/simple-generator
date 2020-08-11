package com.hand.hls.repository;

import com.hand.hls.domain.ColumnInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ColumnInfoRepository extends JpaRepository<ColumnInfo,Integer> {

    /**
     * 查询表信息
     * @param tableName 表格名
     * @return 表信息
     */
    List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName);
}
