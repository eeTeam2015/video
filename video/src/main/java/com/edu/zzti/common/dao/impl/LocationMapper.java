package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.dao.BaseDao;
import com.edu.zzti.common.model.Location;

/**
 * 地区
 */
public interface LocationMapper extends BaseDao<Location> {
    int updateByPrimaryKey(Location record);
}