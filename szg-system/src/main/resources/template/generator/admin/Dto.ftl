package ${package}.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if columns??>
    <#list columns as column>
        <#if column.columnType = 'Date'>
import java.util.Date;
            <#break>
        </#if>
    </#list>
</#if>


@ExtensionAttribute(disable=true)
@Table(name="${tableName}")
@Data
public class ${className} extends BaseDTO {

<#if columns??>
    <#list columns as column>
        <#if column.remark != ''>
    /** ${column.remark} */
        </#if>
        <#if (column.columnKey)?contains("P")>
    @Id
    @GeneratedValue
        </#if>
    @Column(name = "${column.columnName}"<#if (column.columnKey)?contains("U")>,unique = true</#if><#if column.istNotNull && !(column.columnKey)?contains("P")>,nullable = false</#if>)
        <#if column.istNotNull && !(column.columnKey)?contains("P")>
            <#if column.columnType = 'String'>
    @NotBlank
            <#else>
    @NotNull
            </#if>
        </#if>
        <#if column.dateAnnotation??>
            <#if column.dateAnnotation = 'CreationTimestamp'>
    @CreationTimestamp
            <#else>
    @UpdateTimestamp
            </#if>
        </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}
