<fenixs namespace="${className}Repository">
    <fenix id="find${className}Dtl" resultType="${package}.service.dto.${className}Dto">
        SELECT t.* from
        (
            SELECT
            <#if columns??>
                <#list columns as column>
                   t1.${column.columnName} AS ${column.changeColumnName}<#if column_has_next>,</#if>
                </#list>
            </#if>
            FROM
                ${tableName} t1
        )t
        where 1 = 1
        <#if queryColumns??>
            <#list queryColumns as column>
                <#if column.queryType = '='>
        <andEqual field="t.${column.changeColumnName}" value="criteria.${column.changeColumnName}" match="criteria.${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = 'Like'>
        <andLike field="t.${column.changeColumnName}" value="criteria.${column.changeColumnName}" match="criteria.${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = '!='>
        <andNotEqual field="t.${column.changeColumnName}" value="criteria.${column.changeColumnName}" match="criteria.${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = 'NotNull'>
        <andIsNotNull field="t.${column.changeColumnName}" value="criteria.${column.changeColumnName}" match="criteria.${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = '>='>
        <andGreaterThanEqual field="t.${column.changeColumnName}" value="criteria.${column.changeColumnName}" match="criteria.${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = '<='>
        <andLessThanEqual field="t.${column.changeColumnName}" value="criteria.${column.changeColumnName}" match="criteria.${column.changeColumnName} != empty" />
                </#if>
            </#list>
        </#if>
    </fenix>

    <fenix id="find${className}DtlQuickQuery">
        SELECT t.* from
        (
            SELECT
            <#if columns??>
                <#list columns as column>
                   t1.${column.columnName}<#if column_has_next>,</#if>
                </#list>
            </#if>
            FROM
                ${tableName} t1
        )t
        where 1 = 1
        <#if queryColumns??>
            <#list queryColumns as column>
                <#if column.queryType = '='>
        <andEqual field="t.${column.columnName}" value="criteria.${column.changeColumnName}" match="?criteria.?${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = 'Like'>
        <andLike field="t.${column.columnName}" value="criteria.${column.changeColumnName}" match="?criteria.?${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = '!='>
        <andNotEqual field="t.${column.columnName}" value="criteria.${column.changeColumnName}" match="?criteria.?${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = 'NotNull'>
        <andIsNotNull field="t.${column.columnName}" value="criteria.${column.changeColumnName}" match="?criteria.?${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = '>='>
        <andGreaterThan field="t.${column.columnName}" value="criteria.${column.changeColumnName}" match="?criteria.?${column.changeColumnName} != empty" />
                </#if>
                <#if column.queryType = '<='>
        <andLessThan field="t.${column.columnName}" value="criteria.${column.changeColumnName}" match="?criteria.?${column.changeColumnName} != empty" />
                </#if>
            </#list>
        </#if>
    </fenix>
</fenixs>
