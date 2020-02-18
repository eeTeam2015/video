package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * vip编码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_bus_vip_code")
public class VipCode extends BaseModel {
    /**
     * 编码
     */
    private String code;

    /**
     * 是否使用
     */
    private Integer isUse;

    /**
     * 过期时间
     */
    private Date expireTime;
}