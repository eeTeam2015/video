package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 子分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_bus_subclass")
public class SubClass extends BaseModel {
    /**
     * 是否使用
     */
    private Integer isUse;

    /**
     * 名称
     */
    private String name;

    /**
     * 目录id
     */
    private String catalogId;
}