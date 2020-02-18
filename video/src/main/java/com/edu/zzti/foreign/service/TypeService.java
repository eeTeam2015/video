package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.Type;
import java.util.List;

/**
 * 类型service层接口
 */
public interface TypeService {
    String  add(Type type);

    List<Type> listIsUse();

    List<Type> listBySubClassId(String subClassId);

    Type load(String val);

    List<Type> listIsUseBySubClassId(String subClassId);
}
