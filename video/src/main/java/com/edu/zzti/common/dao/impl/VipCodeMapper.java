package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.dao.BaseDao;
import com.edu.zzti.common.model.VipCode;

/**
 * vip编码
 */
public interface VipCodeMapper extends BaseDao<VipCode> {
    int updateByPrimaryKey(VipCode record);
}