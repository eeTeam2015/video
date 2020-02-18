package com.edu.zzti.foreign.service.impl;

import com.edu.zzti.common.dao.impl.DecadeMapper;
import com.edu.zzti.common.model.Decade;
import com.edu.zzti.foreign.service.DecadeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 年代service层接口实现
 */
@Component
public class DecadeServiceImpl implements DecadeService {

    @Resource
    private DecadeMapper decadeMapper;

    /**
     * 添加年代信息
     * @param decade
     * @return
     */
    @Override
    public String add(Decade decade) {
        decade.beforeInsert();
        return decadeMapper.insert(decade) > 0 ? "1" : "0";
    }

    /**
     * 查询在使用的年代
     * @return
     */
    @Override
    public List<Decade> listIsUse() {
        return decadeMapper.selectByIsUse(1);
    }
}
