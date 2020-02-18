package com.edu.zzti.foreign.service.impl;

import com.edu.zzti.common.dao.impl.VipCodeMapper;
import com.edu.zzti.common.model.VipCode;
import com.edu.zzti.foreign.service.VipCodeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 类型service层接口实现
 */
@Component
public class VipCodeServiceImpl implements VipCodeService {
    @Resource
    private VipCodeMapper vipCodeMapper;

    @Override
    public String add(VipCode vipCode) {
        return vipCodeMapper.insert(vipCode) > 0 ? vipCode.getId() : "0";
    }

    @Override
    public List<VipCode> listIsUse() {
        VipCode vipCode = new VipCode();
        vipCode.setIsUse(1);
        return vipCodeMapper.select(vipCode);
    }

    @Override
    public VipCode load(String vipCodeId) {
        return vipCodeMapper.selectByPrimaryKey(vipCodeId);
    }

    @Override
    public int saveAll(List<VipCode> vipCodes) {
        int count=0;
        for (int i = 0; i < vipCodes.size(); i++) {
            try {
                vipCodeMapper.insert(vipCodes.get(i));
                count++;
            }catch (Exception e){
                //不理会
            }
        }
        return count;
    }

    @Override
    public VipCode findByVipCode(String code) {
        VipCode vipCode = new VipCode();
        vipCode.setIsUse(1);
        vipCode.setCode(code);
        List<VipCode> vipCodes = vipCodeMapper.select(vipCode);
        return CollectionUtils.isEmpty(vipCodes) ? null : vipCodes.get(0);
    }

    @Override
    public boolean update(VipCode vipCode) {
        return vipCodeMapper.updateByPrimaryKeySelective(vipCode) > 0;
    }
}
