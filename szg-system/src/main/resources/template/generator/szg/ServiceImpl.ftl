package ${package}.service.impl;

import ${package}.domain.${className};
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
import com.hand.hls.exception.EntityExistException;
            <#break>
        </#if>
    </#list>
</#if>
import ${package}.repository.${className}Repository;
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}Dto;
import ${package}.service.dto.${className}QueryCriteria;
import ${package}.service.mapper.${className}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
<#if hasGetApi>
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
</#if>
<#if pkColumnType = 'Long'>
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
</#if>
<#if pkColumnType = 'String'>
import cn.hutool.core.util.IdUtil;
</#if>
<#if hasExcelApi>
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
</#if>
import com.hand.hls.util.*;
import java.util.*;

/**
* @author ${author}
* @date ${date}
*/
@Service
//@CacheConfig(cacheNames = "${changeClassName}")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Repository ${changeClassName}Repository;

    @Autowired
    private ${className}Mapper ${changeClassName}Mapper;
    <#if hasGetApi>

    @Override
    public Map<String, Object> get(${className}QueryCriteria criteria, Pageable pageable){
        Page<${className}Dto> page = ${changeClassName}Repository.query(criteria, pageable);
        return PageUtils.toPage(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<${className}Dto> getAll(${className}QueryCriteria criteria){
        return ${changeClassName}Repository.queryAll(criteria);
    }

    @Override
    public ${className}Dto getById(${pkColumnType} ${pkChangeColName}) {
        Optional<${className}> optional${className} = ${changeClassName}Repository.findById(${pkChangeColName});
        ValidationUtils.isNull(optional${className}, "${className}", "id", ${pkChangeColName});
        ${className} ${changeClassName} = optional${className}.get();
        return ${changeClassName}Mapper.toDto(${changeClassName});
    }
    </#if>
    <#if hasAddApi>

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className}Dto create(${className} resources) {
        <#if pkColumnType = 'Long'>
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.set${pkCapitalColName}(snowflake.nextId());
        </#if>
        <#if pkColumnType = 'String'>
        resources.set${pkCapitalColName}(IdUtil.simpleUUID());
        </#if>
        <#if columns??>
            <#list columns as column>
            <#if column.columnKey = 'UNI'>
            if(${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}()) != null){
            throw new EntityExistException(${className}.class, "${column.columnName}", resources.get${column.capitalColumnName}());
        }
            </#if>
            </#list>
        </#if>
        return ${changeClassName}Mapper.toDto(${changeClassName}Repository.save(resources));
    }
    </#if>
    <#if hasPutApi>

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
        Optional<${className}> optional${className} = ${changeClassName}Repository.findById(resources.get${pkCapitalColName}());
        ValidationUtils.isNull( optional${className}, "${className}", "id", resources.get${pkCapitalColName}());
        ${className} ${changeClassName} = optional${className}.get();
        <#if columns??>
        <#list columns as column>
        <#if column.columnKey = 'UNI'>
        <#if column_index = 1>
        ${className} ${changeClassName}1 = null;
        </#if>
        ${changeClassName}1 = ${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}());
        if(${changeClassName}1 != null && !${changeClassName}1.get${pkCapitalColName}().equals(${changeClassName}.get${pkCapitalColName}())){
            throw new EntityExistException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
        }
        </#if>
        </#list>
        </#if>
        ${changeClassName}.copy(resources);
        ${changeClassName}Repository.save(${changeClassName});
    }
    </#if>
    <#if hasDelApi>

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(${pkColumnType}[] ids) {
        for (${pkColumnType} id : ids) {
            ${changeClassName}Repository.deleteById(id);
        }
    }
    </#if>
    <#if hasExcelApi>

    @Override
    public void download(List<${className}Dto> list, HttpServletResponse response) throws IOException {
        FileUtils.downloadExcel(list, ${className}Dto.class, response);
    }
    </#if>
}
