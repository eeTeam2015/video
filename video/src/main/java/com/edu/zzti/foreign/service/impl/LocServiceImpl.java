package com.edu.zzti.foreign.service.impl;

import com.edu.zzti.common.dao.impl.LocationMapper;
import com.edu.zzti.common.model.Location;
import com.edu.zzti.foreign.service.LocService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地区service层接口实现
 */
@Component
public class LocServiceImpl implements LocService {

    @Resource
    private LocationMapper locationMapper;

    @Override
    public String add(Location loc) {
        loc.beforeInsert();
        return locationMapper.insert(loc) > 0 ? "1" : "0";
    }

    @Override
    public List<Location> listIsUse() {
        Location location = new Location();
        location.setIsUse(1);
        return locationMapper.select(location);
    }
}
