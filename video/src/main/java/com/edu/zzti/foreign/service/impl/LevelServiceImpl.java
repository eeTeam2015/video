package com.edu.zzti.foreign.service.impl;

import com.edu.zzti.common.dao.impl.LevelMapper;
import com.edu.zzti.common.model.Level;
import com.edu.zzti.foreign.service.LevelService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 级别service层接口实现
 */
@Component
public class LevelServiceImpl implements LevelService {

    @Resource
    private LevelMapper levelMapper;

    /**
     * 添加级别
     *
     * @param level
     * @return
     */
    @Override
    public String add(Level level) {
        level.beforeInsert();
        return levelMapper.insert(level) > 0 ? "1": "0";
    }


    /**
     * 查询在使用的界别类型
     *
     * @return
     */
    @Override
    public List<Level> listIsUse() {
        Level level = new Level();
        level.setIsUse(1);
        return levelMapper.select(level);
    }
}
