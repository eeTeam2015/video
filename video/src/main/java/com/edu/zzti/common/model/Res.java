package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 资源
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_bus_res")
public class Res extends BaseModel {
    /**
     * 集数
     */
    private Integer episodes;

    /**
     * 是否使用
     */
    private Integer isUse;

    /**
     * 链接
     */
    private String link;

    /**
     * 链接类型
     */
    private String linkType;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 影片名称
     */
    private String filmId;
}