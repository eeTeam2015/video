package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.dao.BaseDao;
import com.edu.zzti.common.model.Decade;

import java.util.List;

/**
 * 年份
 */
public interface DecadeMapper extends BaseDao<Decade> {
    int updateByPrimaryKey(Decade record);

    List<Decade> selectByIsUse(int isUse);
}