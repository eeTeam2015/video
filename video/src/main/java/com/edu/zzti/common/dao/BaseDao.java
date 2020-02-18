package com.edu.zzti.common.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Dao层基类
 */
public interface BaseDao <T> extends Mapper<T>, MySqlMapper<T> {

}
