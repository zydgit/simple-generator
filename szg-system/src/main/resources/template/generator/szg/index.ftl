<template>
    <div class="app-container">
        <AdvancedSearch :query="query" :title="$route.name" @refresh="toQuery"<#if hasAddApi> @add="add" </#if>@query="toQuery">
            <#if columns??>
                <#list queryColumns as column>
                    <#if column_index = 4>
                        <template v-slot:fold>
                    </#if>
                    <el-col :span="6">
                        <el-form-item label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>"<#if column.istNotNull> prop="${column.changeColumnName}"</#if>>
                            <#if column.formType = 'Input'>
                                <el-input v-model="query.${column.changeColumnName}" style="width: 224px;"/>
                            <#elseif column.formType = 'Textarea'>
                                <el-input :rows="3" v-model="query.${column.changeColumnName}" type="textarea" style="width: 224px;"/>
                            <#elseif column.formType = 'Radio'>
                                <#if column.dictName??>
                                    <el-radio v-for="item in dictMap['${column.dictName}']" :key="item.id" v-model="query.${column.changeColumnName}" :label="item.value">{{ item.label }}</el-radio>
                                <#else>
                                    未设置字典，请手动设置 Radio
                                </#if>
                            <#elseif column.formType = 'Select'>
                                <#if column.dictName??>
                                    <el-select style="width: 224px;" v-model="query.${column.changeColumnName}" filterable placeholder="请选择">
                                        <el-option
                                                v-for="item in dictMap['${column.dictName}']"
                                                :key="item.id"
                                                :label="item.label"
                                                :value="item.value"/>
                                    </el-select>
                                <#else>
                                    未设置字典，请手动设置 Select
                                </#if>
                            <#elseif column.formType = 'Lov'>
                                <#if column.lovCode??>
                                    <Lover ref="lov" v-model="query.${column.changeColumnName}" code="${column.lovCode}" width="224px;" @select="handleLovRowSelect"/>
                                <#else>
                                    ${column}
                                    未设置lov code，请手动设置
                                </#if>
                            <#else>
                                <el-date-picker v-model="query.${column.changeColumnName}" type="datetime" style="width: 224px;"/>
                            </#if>
                        </el-form-item>
                    </el-col>
                    <#if !column_has_next && (column_index > 3)>
                        </template>
                    </#if>
                </#list>
            </#if>
            <#if hasAddApi>
                <#if hasExcelApi>
                    <template v-slot:other>
                        <el-button :loading="downloading" icon="el-icon-download" size="mini" type="xy-primary" @click="handleExport">
                            导出
                        </el-button>
                    </template>
                </#if>
            <#else>
                <template v-slot:btns>
                    <el-button size="mini" icon="el-icon-search" type="xy-primary" @click="toQuery">查询</el-button>
                    <#if hasExcelApi>
                        <el-button :loading="downloading" icon="el-icon-download" size="mini" type="xy-primary" @click="handleExport">
                            导出
                        </el-button>
                    </#if>
                </template>
            </#if>
        </AdvancedSearch>

        <div class="content-container">
            <el-table v-loading="loading" :data="data" size="mini" border style="width: 100%;">
                <#if columns??>
                    <#list columns as column>
                        <#if column.columnShow>
                            <#if column.dictName??>
                <el-table-column :show-overflow-tooltip="true" prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>">
                    <template slot-scope="scope">
                        {{ dictValueMapLabel(dictMap['${column.dictName}'], scope.row.${column.changeColumnName}) }}
                    </template>
                </el-table-column>
                            <#elseif column.columnType != 'Timestamp'>
                <el-table-column :show-overflow-tooltip="true" prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>"/>
                            <#else>
                <el-table-column :show-overflow-tooltip="true" prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>">
                    <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.${column.changeColumnName}) }}</span>
                    </template>
                </el-table-column>
                            </#if>
                        </#if>
                    </#list>
                </#if>
                <el-table-column v-if="checkPermission(['ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_EDIT','${upperCaseClassName}_DELETE'])" label="操作" width="150px" align="center">
                    <template slot-scope="scope">
                        <el-button v-permission="['ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
                        <pop-confirm
                            v-permission="['ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_DELETE']"
                            :del-loading="delLoading"
                            @confirm="subDelete(scope.row.${pkChangeColName})">
                            <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
                        </pop-confirm>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="footer-container">
            <el-pagination
                :total="total"
                :current-page="page + 1"
                background
                layout="total, prev, pager, next, sizes"
                @size-change="sizeChange"
                @current-change="pageChange"/>
        </div>

        <eForm ref="form" <#if hasDict>:dict-map="dictMap"</#if> :is-add="isAdd" @refresh="toQuery"/>
    </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/initData'
<#if hasDict??>
import initDict from '@/mixins/initDict'
</#if>
<#if apiModule??>
import {del<#if hasExcelApi>, downloadExcel</#if>} from '@/api/${apiModule}/${changeClassName}'
<#else>
import {del<#if hasExcelApi>, downloadExcel</#if>} from '@/api/${changeClassName}'
</#if>
<#if hasTimestamp>
import {parseTime} from '@/utils/index'
</#if>
import eForm from './eForm'

export default {
    components: {eForm},
    mixins: [initData<#if hasDict??>, initDict</#if>],
    data() {
        return {
            <#if hasExcelApi>
            downloading: false,
            </#if>
            delLoading: false
        }
    },
    created() {
        this.$nextTick(() => {
            this.init()
            <#if hasDict??>
            this.getDictMap('<#list dicts as dict>${dict}<#if dict_has_next>,</#if></#list>')
            </#if>
        })
    },
    methods: {
        <#if hasTimestamp>
        parseTime,
        </#if>
        checkPermission,
        beforeInit() {
            this.url = 'api/${changeClassName}'
            const sort = '${pkChangeColName},desc'
            this.params = {page: this.page, size: this.size, sort: sort}
            Object.keys(this.query).forEach(key => {
                if (this.query[key]) {
                    this.params[key] = this.query[key]
                }
            })
            return true
        },
        <#if columns??>
        <#list queryColumns as column>
        <#if column.formType = 'Lov'>
        handleLovRowSelect(row){},
        <#break>
        </#if>
        </#list>
        </#if>
        subDelete(${pkChangeColName}) {
            this.delLoading = true
            del([${pkChangeColName}]).then(res => {
                this.delLoading = false
                this.dleChangePage()
                this.init()
                this.$notify({
                    title: '删除成功',
                    type: 'success',
                    duration: 2500
                })
            }).catch(err => {
                this.delLoading = false
                console.log(err.response.data.message)
            })
        },
        add() {
            this.isAdd = true
            this.$refs.form.dialog = true
        },
        edit(data) {
            this.isAdd = false
            const _this = this.$refs.form
            _this.form = {...data}
            _this.dialog = true
        }<#if hasExcelApi>,</#if>
        <#if hasExcelApi>
        handleExport() {
            this.downloading = true
            downloadExcel(this.params, ${apiAlias})
                .then()
                .catch(err => {
                    console.log(err)
                    this.$notify({
                        title: '下载失败',
                        type: 'error',
                        duration: 2500
                    })
                })
                .finally(() => {
                    this.downloading = false
                })
        }
        </#if>
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
