package com.edu.zzti.foreign.service.impl;

import com.edu.zzti.common.dao.impl.SubClassMapper;
import com.edu.zzti.common.model.SubClass;
import com.edu.zzti.foreign.service.SubClassService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.List;

/**
 * 二级目录service层接口实现
 */
@Component
public class SubClassServiceImpl implements SubClassService {


    @Resource
    private SubClassMapper subClassMapper;


    @Override
    public String add(SubClass subClass) {
        subClass.beforeInsert();
        return subClassMapper.insert(subClass) > 0 ? "1" : "0";
    }

    @Override
    public SubClass load(String subClassId) {
        return subClassMapper.selectByPrimaryKey(subClassId);
    }

    @Override
    public List<SubClass> listByCataLogId(String catalogId) {
        SubClass subClass = new SubClass();
        subClass.setCatalogId(catalogId);
        return subClassMapper.select(subClass);
    }

    @Override
    public List<SubClass> listIsUse(String catalogId) {
        SubClass subClass = new SubClass();
        subClass.setIsUse(1);
        subClass.setCatalogId(catalogId);
        return subClassMapper.select(subClass);
    }

    @Override
    public List<SubClass> listIsUse(List<String> cataLogIdList) {
        Example example = Example.builder(SubClass.class).where(WeekendSqls.<SubClass>custom()
                .andIn(SubClass::getCatalogId, cataLogIdList)
        ).build();
        return subClassMapper.selectByExample(example);
    }
}
