package ${package}.repository;

import ${package}.domain.${className};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
<#if hasGetApi>
import com.blinkfox.fenix.jpa.QueryFenix;
import ${package}.service.dto.${className}Dto;
import ${package}.service.dto.${className}QueryCriteria;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
</#if>
/**
* @author ${author}
* @date ${date}
*/
public interface ${className}Repository extends JpaRepository<${className}, ${pkColumnType}>, JpaSpecificationExecutor<${className}> {
    <#if hasGetApi>

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Page<${className}Dto>
    */
    @QueryFenix(value = "${className}Repository.find${className}Dtl", nativeQuery = true)
    Page<${className}Dto> query(@Param("criteria") ${className}QueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<${className}Dto>
    */
    @QueryFenix(value = "${className}Repository.find${className}Dtl", nativeQuery = true)
    List<${className}Dto> queryAll(@Param("criteria") ${className}QueryCriteria criteria);

    </#if>

    /**
    * 快速查询 - 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Page<Map>
    */
    @QueryFenix(value = "${className}Repository.find${className}DtlQuickQuery", nativeQuery = true)
    Page<Map> quickQuery(@Param("criteria") Map criteria, Pageable pageable);

    /**
    * 快速查询 - 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<Map>
    */
    @QueryFenix(value = "${className}Repository.find${className}DtlQuickQuery", nativeQuery = true)
    List<Map> quickQueryAll(@Param("criteria") Map criteria);
    <#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
    /**
    * 根据 ${column.capitalColumnName} 查询
    * @param ${column.columnName} /
    * @return /
    */
    ${className} findBy${column.capitalColumnName}(${column.columnType} ${column.columnName});
        </#if>
    </#list>
    </#if>
}
