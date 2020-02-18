package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.CataLog;
import java.util.List;

/**
 * 一级目录service层接口
 */
public interface CataLogService {

    String  add(CataLog cataLog);

    List<CataLog> listIsUse();

    CataLog load(String cataLogId);

}
