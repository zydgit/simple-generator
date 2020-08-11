package ${package}.service.dto;

import lombok.Data;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;
<#if pkColumnType = 'Long'>
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
</#if>
<#if hasExcelApi>
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
</#if>
<#if columns??>
    <#list columns as column>
        <#if column.columnType = 'Date'>
import java.util.Date;
            <#break>
        </#if>
    </#list>
</#if>

/**
* @author ${author}
* @date ${date}
*/
@Data
<#if hasExcelApi>
@ExcelIgnoreUnannotated
</#if>
public class ${className}Dto implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if column.remark != ''>
    /** ${column.remark} */
    </#if>
    <#if hasExcelApi>
    @ExcelProperty(value = "${column.remark}")
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}
