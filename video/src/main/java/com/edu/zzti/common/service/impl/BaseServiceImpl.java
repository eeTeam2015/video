package com.edu.zzti.common.service.impl;

import com.edu.zzti.common.dao.BaseDao;
import com.edu.zzti.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service层基类实现
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;

    @Override
    public int deleteByPrimaryKey(String id) {
        return baseDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T t) {
        return baseDao.insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return baseDao.insertSelective(t);
    }

    @Override
    public T selectByPrimaryKey(String id) {
        return baseDao.selectByPrimaryKey(id);
    }
}
