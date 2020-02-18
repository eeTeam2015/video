package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.model.Res;
import com.edu.zzti.common.dao.BaseDao;

/**
 * 资源
 */
public interface ResMapper extends BaseDao<Res> {
    int updateByPrimaryKey(Res record);
}