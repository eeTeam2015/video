package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.model.SubClass;
import com.edu.zzti.common.dao.BaseDao;

/**
 * 子分类
 */
public interface SubClassMapper extends BaseDao<SubClass> {
    int updateByPrimaryKey(SubClass record);
}