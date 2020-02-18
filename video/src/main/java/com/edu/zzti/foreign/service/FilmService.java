package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.Film;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 影片service层接口
 */
public interface FilmService {
    String add(Film film);

    Film load(String filmId);

    boolean update(Film film);

    PageInfo<Film> getPage(Film ob, int pc, int ps);

    List<Film> listByTypeId(String type_id);

    List<Film> listByCataLogId(String id);

    List<Film> listByEvaluation(String id);

    int getCountAll();

    int getCountByCataLog(String id);

    List<Film> selectByCondition(Film film);
}
