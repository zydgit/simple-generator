<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package}.mapper.${className}Mapper">

    <resultMap id="queryDtlResultMap" type="java.util.Map">
        <#list columns as column>
            <result column="${column.columnName!""}" property="${column.changeColumnName}"/>
        </#list>
    </resultMap>


    <select id="queryDtl" resultMap="queryDtlResultMap" parameterType="java.util.Map">
        SELECT
         <#list columns as column>
            tab.${column.columnName!""}
         </#list>
        FROM
        ${tableName} tab
        WHERE 1=1

          <#if queryColumns??>
              <#list queryColumns as column>
                  <#if column.queryType = '='>
                    <if test="${column.changeColumnName} != null">
                        and tab.${column.columnName!""} = ${column.changeColumnName}
                    </if>
                  </#if>
                  <#if column.queryType = 'Like'>
                    <if test="${column.changeColumnName} != null">
                        and tab.${column.columnName!""} like concat(concat('%',${column.changeColumnName}),'%')
                    </if>
                  </#if>
                  <#if column.queryType = '!='>
                       <if test="${column.changeColumnName} != null">
                           and tab.${column.columnName!""} != ${column.changeColumnName}
                       </if>
                  </#if>
                  <#if column.queryType = 'NotNull'>
                     and tab.${column.columnName!""} is not null
                  </#if>
                  <#if column.queryType = '>='>
                     <if test="${column.changeColumnName} != null">
                         and tab.${column.columnName!""} &gt;= ${column.changeColumnName}
                     </if>
                  </#if>
                  <#if column.queryType = '<='>
                     <if test="${column.changeColumnName} != null">
                         and tab.${column.columnName!""} &lt;= ${column.changeColumnName}
                     </if>
                  </#if>
              </#list>
          </#if>
    </select>


</mapper>