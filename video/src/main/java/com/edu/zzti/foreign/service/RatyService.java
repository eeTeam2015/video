package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.Raty;
import java.util.List;


/**
 * 评分service接口
 */
public interface RatyService {

    String  add(Raty raty);

    List<Raty> listALLByFilmId(String filmId);

    int getCountByfilmId(String filmId);
}
