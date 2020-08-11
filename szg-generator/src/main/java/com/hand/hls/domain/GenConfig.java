package com.hand.hls.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 代码生成配置
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "FST_GEN_CONFIG")
public class GenConfig {

    public GenConfig(String tableName) {
        this.cover = false;
        this.moduleName = "";
        this.author = "zy";
        this.prefix = "FST_";
        this.pack = "com.hand.hls.*";
        this.frontTemplate = "DEFAULT";
        this.tableName = tableName;
        this.section = "BOTH";
        this.apiType = "ADD,DEL,PUT,GET";
    }


    @Id
    @SequenceGenerator(name = "FST_GEN_CONFIG_S", sequenceName = "FST_GEN_CONFIG_S", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FST_GEN_CONFIG_S")
    @Column(name = "id")
    private Integer id;

    @NotBlank
    private String tableName;

    /** 接口名称 **/
    private String apiAlias;

    /** 包路径 */
    private String pack;

    /** 模块名 */
    @Column(name = "module_name")
    private String moduleName;

    /** 前端文件路径 */
    private String path;

    /** 前端接口模块名 */
    @Column(name = "api_module")
    private String apiModule;

    /** 前端接口路径 */
    @Column(name = "api_path")
    private String apiPath;

    /** 作者 */
    private String author;

    /** 表前缀 */
    private String prefix;

    /** 是否覆盖 */
    private Boolean cover;

    /** 前端模板 */
    @Column(name = "front_template")
    private String frontTemplate;

    /** 生成的代码部分 */
    @Column(name = "section")
    private String section;

    /** 接口类型 */
    @Column(name = "api_type")
    private String apiType;
}
