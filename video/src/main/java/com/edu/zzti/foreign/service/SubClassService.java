package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.SubClass;
import java.util.List;

/**
 * 二级目录service层接口
 */
public interface SubClassService {

    String add(SubClass subClass);

    SubClass load(String subClassId);

    List<SubClass> listByCataLogId(String cataLogId);

    List<SubClass> listIsUse(String cataLogId);

    List<SubClass> listIsUse(List<String> cataLogIdList);
}
