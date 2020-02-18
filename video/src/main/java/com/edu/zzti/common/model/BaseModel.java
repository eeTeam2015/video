package com.edu.zzti.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

/**
 * 为所有实体的父类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {

    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * insert 方法执行之前
     */
    public void beforeInsert(){
        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;
        this.id = UUID.randomUUID().toString().replaceAll("\\-", "");
    }

    /**
     * update 方法执行之前
     */
    public void beforeUpdate(){
        Date date = new Date();
        this.updateTime = date;
    }
}
