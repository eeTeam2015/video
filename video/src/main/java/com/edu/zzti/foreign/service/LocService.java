package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.Location;
import java.util.List;

/**
 * 地区service层接口
 */
public interface LocService {
    String add(Location loc);

    List<Location> listIsUse();
}
