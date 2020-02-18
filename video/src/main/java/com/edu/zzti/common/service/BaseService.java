package com.edu.zzti.common.service;

/**
 * Service层基类
 */
public interface BaseService <T>{
    /**
     * deleteByPrimaryKey
     * @param id
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * insert
     * @param t
     * @return int
     */
    int insert(T t);

    /**
     * insertSelective
     * @param t
     * @return int
     */
    int insertSelective(T t);

    /**
     * selectByPrimaryKey
     * @param id
     * @return com.sunkl.my.common.model.SysRole
     */
    T selectByPrimaryKey(String id);
}
