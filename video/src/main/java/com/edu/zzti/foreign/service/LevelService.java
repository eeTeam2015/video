package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.Level;
import java.util.List;

/**
 * 级别service层接口
 */
public interface LevelService {
    String add(Level level);

    List<Level> listIsUse();
}
