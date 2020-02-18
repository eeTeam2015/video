package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.model.Type;
import com.edu.zzti.common.dao.BaseDao;

/**
 * 类型
 */
public interface TypeMapper extends BaseDao<Type> {
    int updateByPrimaryKey(Type record);
}