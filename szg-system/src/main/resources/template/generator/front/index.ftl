<?xml version="1.0" encoding="UTF-8"?>
<h:screen xmlns:h="http://www.hand-china.com/hap">
    <link rel="stylesheet" href="<#noparse>${base.contextPath}</#noparse>/resources/css/template/rpt-base.css"/>
    <script src="<#noparse>${base.contextPath}</#noparse>/lib/kendoui/js/jszip.min.js"></script>


     <#if columns??>
         <#list queryColumns as column>
             <#if column.formType = 'Select' >
                 <#if column.dictName??>
                 <script src="<#noparse>${base.contextPath}</#noparse>/common/code?${column.dictName}=${column.dictName}" type="text/javascript"></script>
                 </#if>
             </#if>
         </#list>
     </#if>


    <style>
        .queryitem{
            float: left;
        }
        .container .querybar .queryitem {
            margin-bottom: 10px;
            overflow: hidden;
            width: 30%;
        }
        .container .querybar .queryitem .item-cont {
            float: left;
            width: 62%;
            line-height: 40px;
        }
        .container .querybar .queryitem .item-title {
            float: left;
            width: 38%;
            line-height: 40px;
            font-size: 14px;
            font-weight: bold;
            padding: 0px;
            margin: 0px;
        }
        .container{
            width: auto;
        }
        .container .querybar .querybtn {
            padding: 6px;
            position: initial;
            color: white;
            width: 70px;
            height: 30px;
            margin-left: 15px;
            border: none;
            cursor: pointer;
            float: right;
            text-align: center;
            display: inline-block;
            background-color: #00b3b3;
            color: #fff;
        }
        .container .querybar {
            padding: 25px 12px 140px 12px;
            position: relative;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 3px 3px 3px rgb(204, 204, 204);
        }
    </style>

    <script id="scriptA"><![CDATA[

    (function ($,kendo,undefined) {
        var Models = [];

        Models.push(Model = {
            selector: '.container',
            data: {
                params: kendo.observable({
                }),
                dataSource: 'gridDataSource',
                parameterMap: function (options) {
                    return JSON.stringify(Hls.prepareQueryParameter(Model.params, options), function(key, value){
                        if(Array.isArray(value) && value.length === 0) {
                            return undefined;
                        }
                        return value;
                    });
                }
            },
            methods: {
                init: function () {
                    var that = this,
                        $find = $(that.selector).find.bind($(that.selector));

                    // 查询
                    $find('button.common').click(function () {
                        window[that.dataSource].page(1);
                    });

                    // 重置
                    $find('button.reset').click(function () {
                        var propertyName,
                            params = that.params;
                        for (propertyName in params) {
                            if (params.hasOwnProperty(propertyName) && ['uid', '_events', '_handlers'].indexOf(propertyName) < 0) {
                                if (Array.isArray(params[propertyName]) || params[propertyName].pop !== undefined) {
                                    params.set(propertyName, [])
                                } else {
                                    params.set(propertyName, '');
                                }
                                delete params[propertyName]
                            }
                        }
                    });

                    // 更多
                    $find('div.extra').click(function () {
                        var $extra = $('div.extra-block'),
                            $img = $('.extra-icon');
                        if ($extra.is(':hidden')) {
                            $img.attr('src', '<#noparse>${base.contextPath}</#noparse>/resources/images/VAT/last.png');
                        } else {
                            $img.attr('src', '<#noparse>${base.contextPath}</#noparse>/resources/images/VAT/next.png');
                        }
                        $extra.slideToggle();
                    });

                    $('.hlsgridbox-btn-a-img').unbind()
                        .click(function () {
                            var grid = $("#grid").data("kendoGrid");
                            grid.setOptions({
                                excel: {
                                    fileName: "${apiAlias}.xlsx",
                                    allPages:true
                                }
                            });
                            grid.saveAsExcel();
                        });

                    kendo.init($('.querybar'));
                    kendo.bind($('.querybar'), Model.params);
                },

                textOverHide:function(property){
                    return function (dataItem) {
                        return '<p class="hls-text-len-hidden" style="width: inherit;padding:0;margin: 0">' + (dataItem[property] || '') + '</p>';
                    }
                },

                openDetailPage:function(ev) {
                    var dataItem = this.dataItem($(ev.target).closest('tr'));
                    // dataItem 是你点击行的数据
                }
            }
        });

        Models.map(function (tabModel) {
            var name,
                data = tabModel.data || {},
                methods = tabModel.methods || {};
            for (name in data) {
                tabModel[name] = data[name];
            }
            for (name in methods) {
                tabModel[name] = methods[name];
            }
        });
    })($, kendo, undefined);

    $(function () {
        Model.init();
    });
    ]]></script>

    <h:dataSource id="gridDataSource" batch="true"  serverPaging="true">
        <h:transport parameterMap="Model.parameterMap">
            <h:read url="<#noparse>${base.contextPath}</#noparse>/${changeClassName}/query/dtl" type="POST" contentType="application/json"
                    dataType="json" />
        </h:transport>
        <h:schema data="rows" total="total">
            <h:model>
                <h:fields>
                    <#if columns??>
                        <#list queryColumns as column>
                            <#if column.formType = 'Date' >
                                <h:item name="${column.changeColumnName}" type="date" />
                            </#if>
                        </#list>
                    </#if>
                </h:fields>
            </h:model>
        </h:schema>
    </h:dataSource>

    <body>
    <div class="container">
        <div class="querybar">
                <#list queryColumns as column>
                    <div class="queryitem">
                        <h5 class="item-title"><#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if></h5>
                        <div class="item-cont">
                              <#if column.formType = 'Input'>
                                  <input class="cont-ipt" data-bind="value:${column.changeColumnName}" type="text" data-role="maskedtextbox" />
                              <#elseif column.formType = 'Textarea'>
                                 <textarea data-bind="value:${column.changeColumnName}" class="cont-ipt" data-role="textarea"></textarea>
                              <#elseif column.formType = 'Select'>
                                  <#if column.dictName??>
                                   <input class="cont-ipt" id="${column.changeColumnName}" data-bind="value:${column.changeColumnName}" type="text" data-role="combobox" />
                                    <script>
                                        $("#${column.changeColumnName}").kendoComboBox({
                                            valuePrimitive: true,
                                            dataTextField: "meaning",
                                            dataValueField: "value",
                                            dataSource:${column.dictName},
                                        });
                                    </script>
                                  <#else>
                                    未设置字典，请手动设置 Select
                                  </#if>

                              <#else>
                                <input type="text" style="width: 70%" id="${column.changeColumnName}" data-role="datepicker"
                                       data-bind="value:${column.changeColumnName}" class="k-datepicker" />
                                <script>
                                    $("#${column.changeColumnName}").kendoDatePicker({
                                        format: "{0:yyyy/MM/dd}"
                                    })
                                </script>
                              </#if>
                        </div>
                    </div>
                </#list>
            <div style="float: right">
                <button class="querybtn common">查询</button>
                <button class="querybtn reset">重置</button>
            </div>
        </div>
        <div class="grid">
            <h:hlsGridBox hlsGridId="grid" title="${apiAlias}" custom1Image="/resources/images/MAIN/hlsgridexcel.png" hlsBtnType="custom1">
                <h:hlsGrid id="grid" dataSource="gridDataSource" autoResize="true" resizable="true" >
                    <h:pageable pageSizes="10,20,50,100,500" buttonCount="5" refresh="true">
                    </h:pageable>
                    <h:columns>
                       <#list columns as column>
                           <#if column.columnShow>
                               <#if column.columnType != 'Timestamp'>
                                <h:column field="${column.changeColumnName}" title="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>" width="80" >
                                    <h:headerAttributes style="text-align:center"/>
                                    <h:attributes style="text-align:center"/>
                                </h:column>
                               <#else>
                               <h:column field="${column.changeColumnName}" format="{0:yyyy-MM-dd}" title="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>" width="80" >
                                   <h:headerAttributes style="text-align:center"/>
                                   <h:attributes style="text-align:center"/>
                               </h:column>
                               </#if>
                           </#if>
                       </#list>
                    </h:columns>
                </h:hlsGrid>
            </h:hlsGridBox>
        </div>
    </div>
    </body>

</h:screen>