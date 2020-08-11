package com.hand.hls.util;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ResultUtils {
    public static List<Map> mapKeyToCamelCase(List<Map> listMap) {

        List<Map> result = new ArrayList<>();

        listMap.forEach(map->{
            Map newWap = new HashMap();

            map.forEach((key,value) -> {
                String camelCaseMapKey  = StringUtils.toCamelCase(String.valueOf(key));
                newWap.put(camelCaseMapKey, value);
            });

            result.add(newWap);
        });

        return result;
    }

    public static Map generalReturnModel(StringBuilder sql, Pageable pageable, Consumer<List<Object>> biConsumer) {

        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");

        List<Object> queryList = new ArrayList<>();

        biConsumer.accept(queryList);

        Integer count = 0;

        if(pageable != null) {
            count = jdbcTemplate.queryForObject(SqlUtils.toCountSql(sql.toString()), queryList.toArray(),Integer.class);
            SqlUtils.toPageSql(sql, pageable.getPageNumber() * pageable.getPageSize() + 1, (pageable.getPageNumber()+1) * pageable.getPageSize());
        }
        List<Map> listMap = jdbcTemplate.query(sql.toString(), queryList.toArray(), (rs, rowNum) -> {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount =  metaData.getColumnCount();
            Map row = new HashMap(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                int columnType = metaData.getColumnType(i);
                if(columnType == Types.NUMERIC) {
                    row.put(StringUtils.toCamelCase(columnName), rs.getBigDecimal(columnName));
                }
                else if(columnType == Types.REAL || columnType == Types.TIMESTAMP) {
                    row.put(StringUtils.toCamelCase(columnName), rs.getTimestamp(columnName));
                } else {
                    row.put(StringUtils.toCamelCase(columnName), rs.getString(columnName));
                }
            }
            return row;
        });
        return PageUtils.toPage(listMap,count);
    }
}
