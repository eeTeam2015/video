package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.dao.BaseDao;
import com.edu.zzti.common.model.CataLog;

/**
 * 目录
 */
public interface CataLogMapper extends BaseDao<CataLog> {
    int updateByPrimaryKey(CataLog record);
}