package com.hand.hls.util;


public class SqlUtils {

    public static String toCountSql(String sql) {
        return "select count(1) from ( " + sql + ")";
    }

    public static String toPageSql(String sql, Integer start, Integer end) {
        return "select t.* from (select rownum as row_index, t.* from ( " + sql + ") t where rownum <= " + end + " ) t where t.row_index >= " + start;
    }

    public static void toPageSql(StringBuilder sql, Integer start, Integer end) {
        sql.insert(0, "select t.* from (select rownum as row_index, t.* from ( ")
                .append(") t where rownum <= ")
                .append(end)
                .append(" ) t where t.row_index >= ")
                .append(start);
    }

    public static String packSql(String sql) {
        return "select t.* from ( " + sql + ") t where 1 = 1 ";
    }

}
