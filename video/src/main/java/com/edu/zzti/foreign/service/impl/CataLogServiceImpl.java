package com.edu.zzti.foreign.service.impl;
import com.edu.zzti.common.dao.impl.CataLogMapper;
import com.edu.zzti.common.model.CataLog;
import com.edu.zzti.foreign.service.CataLogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 一级目录service层接口实现
 */
@Component
public class CataLogServiceImpl implements CataLogService {

    @Resource
    private CataLogMapper cataLogMapper;

    /**
     * 添加一级目录
     * @param cataLog
     * @return
     */
    @Override
    public String add(CataLog cataLog) {
        cataLog.beforeInsert();
        return cataLogMapper.insert(cataLog) > 0 ? "1" : "0";
    }

    /**
     * 查询所有在使用的一级目录
     * @return
     */
    @Override
    public List<CataLog> listIsUse() {
        CataLog cataLog = new CataLog();
        cataLog.setIsUse(1);
        return cataLogMapper.select(cataLog);
    }

    @Override
    public CataLog load(String cataLogId) {
        return cataLogMapper.selectByPrimaryKey(cataLogId);
    }
}
