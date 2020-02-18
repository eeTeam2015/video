package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.VipCode;
import java.util.List;

/**
 * 类型service层接口
 */
public interface VipCodeService {

    String  add(VipCode vipCode);

    List<VipCode> listIsUse();

    VipCode load(String val);

    int saveAll(List<VipCode> vipCodes);

    VipCode findByVipCode(String code);

    boolean update(VipCode vipCode);
}
