package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.hand.hls.domain.BaseDomain;
import javax.persistence.*;
<#if isNotNullColumns??>
import javax.validation.constraints.*;
</#if>
<#if hasDateAnnotation>
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
</#if>
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

/**
* @author ${author}
* @date ${date}
*/
@Entity
@Data
@Table(name="${tableName}")
public class ${className} extends BaseDomain {
<#if columns??>
    <#list columns as column>

    <#if column.remark != ''>
    /** ${column.remark} */
    </#if>
    <#if (column.columnKey)?contains("P")>
    @Id
    @SequenceGenerator(name = "${tableName}_S", sequenceName = "${tableName}_S", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "${tableName}_S")
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

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
