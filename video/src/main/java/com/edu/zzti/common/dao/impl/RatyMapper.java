package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.model.Raty;
import com.edu.zzti.common.dao.BaseDao;

/**
 * 评分
 */
public interface RatyMapper extends BaseDao<Raty> {
    int updateByPrimaryKey(Raty record);
}