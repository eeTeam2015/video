package com.edu.zzti.foreign.service.impl;

import com.edu.zzti.common.dao.impl.ResMapper;
import com.edu.zzti.common.model.Res;
import com.edu.zzti.foreign.service.ResService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源service层接口实现
 */
@Component
public class ResServiceImpl implements ResService {

    @Resource
    private ResMapper resMapper;

    @Override
    public String add(Res res) {
        res.setIsUse(1);
        res.beforeInsert();
        return resMapper.insert(res) > 0 ? "1" : "0";
    }

    /**
     * 查询该filmId的资源
     * @param filmId
     * @return
     */
    @Override
    public List<Res> listByFilmId(String filmId) {
        Res res = new Res();
        res.setFilmId(filmId);
        return resMapper.select(res);
    }

    @Override
    public boolean delete(String resId) {
        return resMapper.deleteByPrimaryKey(resId) > 0;
    }

    /**
     * 更改在离线
     * @param resId
     * @return
     */
    @Override
    public boolean updateIsUse(String resId) {
        Res res = resMapper.selectByPrimaryKey(resId);
        int isUse = res.getIsUse();
        if (isUse == 1) {
            res.setIsUse(0);
        } else {
            res.setIsUse(1);
        }
        return resMapper.updateByPrimaryKeySelective(res) > 0;
    }

    @Override
    public List<Res> listByLinkType(String filmId,String linkType) {
        Res res = new Res();
        res.setFilmId(filmId);
        res.setLinkType(linkType);
        return resMapper.select(res);
    }
}
