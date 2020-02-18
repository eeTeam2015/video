package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 影片
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_bus_film")
public class Film extends BaseModel {
    /**
     * 演员
     */
    private String actor;

    /**
     * 目录名称
     */
    private String cataLogName;

    /**
     * 目录id
     */
    private String cataLogId;

    /**
     * 评价
     */
    private Double evaluation;

    /**
     * 海报
     */
    private String image;

    /**
     * 是否在使用
     */
    private Integer isUse;

    /**
     * 地区名称
     */
    private String locName;

    /**
     * 地区id
     */
    private String locId;

    /**
     * 影片名称
     */
    private String name;

    /**
     * 年代
     */
    private String onDecade;

    /**
     * 分辨率
     */
    private String resolution;

    /**
     * 状态
     */
    private String status;

    /**
     * 子分类名称
     */
    private String subClassName;

    /**
     * 子分类id
     */
    private String subClassId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型id
     */
    private String typeId;

    /**
     * 是否vip
     */
    private Integer isVip;

    /**
     * 情节
     */
    private String plot;
}