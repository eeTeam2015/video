package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.Res;
import java.util.List;

/**
 * 资源service层接口
 */
public interface ResService {
    String add(Res res);

    List<Res> listByFilmId(String filmId);

    boolean delete(String res_id);

    boolean updateIsUse(String res_id);

    List<Res> listByLinkType(String filmId, String linkType);
}
