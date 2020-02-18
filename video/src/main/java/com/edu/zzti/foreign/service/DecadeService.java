package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.Decade;

import java.util.List;

/**
 * 年代service层接口
 */
public interface DecadeService {

    String add(Decade decade);

    List<Decade> listIsUse();
}
