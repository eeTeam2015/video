package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.dao.BaseDao;
import com.edu.zzti.common.model.Level;

/**
 * 影片级别
 */
public interface LevelMapper extends BaseDao<Level> {
    int updateByPrimaryKey(Level record);
}