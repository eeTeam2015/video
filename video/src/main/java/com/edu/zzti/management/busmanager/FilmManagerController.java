package com.edu.zzti.management.busmanager;

import com.edu.zzti.common.model.CataLog;
import com.edu.zzti.common.model.Film;
import com.edu.zzti.common.model.Res;
import com.edu.zzti.common.model.SubClass;
import com.edu.zzti.common.model.Type;
import com.edu.zzti.foreign.service.CataLogService;
import com.edu.zzti.foreign.service.DecadeService;
import com.edu.zzti.foreign.service.FilmService;
import com.edu.zzti.foreign.service.LocService;
import com.edu.zzti.foreign.service.ResService;
import com.edu.zzti.foreign.service.SubClassService;
import com.edu.zzti.foreign.service.TypeService;
import com.edu.zzti.common.model.*;
import com.edu.zzti.common.util.FileOperate;
import com.edu.zzti.common.util.Tools;
import com.edu.zzti.foreign.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 影片管理
 */
@Controller
@Api(tags = "影片管理{影视管理:/videoManager}")
@RequestMapping("/video/manager")
public class FilmManagerController {

    @Resource
    private CataLogService cataLogService;

    @Resource
    private SubClassService subClassService;

    @Resource
    private TypeService typeService;

    @Resource
    private FilmService filmService;

    @Resource
    private ResService resService;

    @Resource
    private DecadeService decadeService;

    @Resource
    private LocService locService;

    /**
     * 影片列表页面
     * @param map
     * @param request
     */
    @ApiOperation(value = "影片列表页面")
    @GetMapping(value = {"/list.html",""})
    public String list(ModelMap map, HttpServletRequest request) {
        String name = request.getParameter("name");
        if (Tools.notEmpty(name)) {
            map.addAttribute("name", name);
        }
        Film film = new Film();
        film.setName(name);
        List<Film> filmList = filmService.selectByCondition(film);
        map.addAttribute("filmList", filmList);
        return "manager/list";
    }

    /**
     * 影片管理
     * @param map
     * @param filmId
     * @return
     */
    @GetMapping(value = "/film.html")
    @ApiOperation(value = "添加/修改影片信息")
    public String film(ModelMap map, String filmId) {
        if (Tools.notEmpty(filmId)) {
            //获取影片信息
            map.addAttribute("film", filmService.load(filmId));

            //获取影片关联资源信息
            List<Res> list = resService.listByFilmId(filmId);
            if (!CollectionUtils.isEmpty(list)) {
                map.addAttribute("res", list);
            }else {
                map.addAttribute("res", Collections.emptyList());
            }
        }
        //获取年代信息
        map.addAttribute("decadeList", decadeService.listIsUse());
        map.addAttribute("cataLogList",cataLogService.listIsUse());
        map.addAttribute("locList",locService.listIsUse());
        return "manager/film";
    }

    @PostMapping(value = "/addFilm.html")
    @ResponseBody
    @ApiOperation(value = "添加影片")
    public String addFilm(Film film) {
        /**
         * 初始化参数
         */
        film.setIsUse(1);

        /**
         * 添加地区
         */
        String id = filmService.add(film);
        return id;
    }

    /**
     * 添加资源
     *
     * @param res
     * @param filmId
     * @return
     */
    @PostMapping(value = "/addRes.html")
    @ResponseBody
    @ApiOperation(value = "添加资源")
    public String addRes(Res res, String filmId) {
        /**
         * 初始化参数
         */
        res.setIsUse(1);//设置默认在使用

        Film film = filmService.load(filmId);
        res.setFilmId(filmId);
        res.setUpdateTime(new Date());
        /**
         * 多资源上传
         */
        String id = "";
        if (res.getName().contains("@@")) {
            String resName[] = res.getName().trim().split("##");//xxxx@@集&集数开始&集数结束&分割符号
            String name = resName[0];
            int begin = Integer.parseInt(resName[1]);
            int end = Integer.parseInt(resName[2]);
            String flag = resName[3];
            String res_link_valArray[] = res.getLink().replaceAll("\\n", "").split(flag);
            int cz = begin - 1;
            for (int x = begin; x <= end; x++) {
                res.setName(name.replace("@@", x + ""));
                res.setEpisodes(x);
                res.setLink(flag + res_link_valArray[x - cz]);
                id = resService.add(res);
            }
        } else {
            /**
             * 添加地区
             */
            id = resService.add(res);
        }

        /**最近更新时间*/
        film.setUpdateTime(new Date());
        filmService.update(film);
        return id;
    }

    /**
     * 更改影片信息
     *
     * @param filmId
     * @param key
     * @param val
     * @return
     */
    @PostMapping(value = "/updateFilmInfo.html")
    @ResponseBody
    @ApiOperation(value = "更改影片信息")
    public String updateFilmInfo(String filmId, String key, String val, HttpSession session) {
        Film film = filmService.load(filmId);
        switch (key) {
            case "name":
                film.setName(val);
                break;
            case "image":
                FileOperate.delFile(session.getServletContext().getRealPath("/" + film.getImage()));
                film.setImage(val);
                break;
            case "onDecade":
                film.setOnDecade(val);
                break;
            case "status":
                film.setStatus(val);
                break;
            case "resolution":
                film.setResolution(val);
                break;
            case "typeName":
                film.setTypeName(val);
                break;
            case "type_id":
                film.setTypeId(val);
                Type type = typeService.load(val);

                SubClass subClass = subClassService.load(type.getSubclassId());
                film.setSubClassId(type.getSubclassId());
                film.setSubClassName(subClass.getName());

                CataLog cataLog = cataLogService.load(subClass.getCatalogId());
                film.setCataLogId(cataLog.getId());
                film.setCataLogName(cataLog.getName());
                break;
            case "actor":
                film.setActor(val);
                break;
            case "locName":
                film.setLocName(val);
                break;
            case "loc_id":
                film.setLocId(val);
                break;
            case "plot":
                film.setPlot(val);
                break;
            case "isVip":
                film.setIsVip(Integer.valueOf(val));
                break;
        }
        boolean isOK = filmService.update(film);
        if (isOK) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 删除资源
     *
     * @param resId
     * @return
     */
    @PostMapping(value = "/delRes.html")
    @ResponseBody
    @ApiOperation(value = "删除资源")
    public  String delRes(String resId) {
        if (resService.delete(resId)) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 更新资源在离线
     *
     * @param resId
     * @return
     */
    @PostMapping(value = "/updateIsUse.html")
    @ResponseBody
    @ApiOperation(value = "更新资源在离线")
    public String updateIsUse(String resId) {
        if (resService.updateIsUse(resId)) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 获取subClass二级目录信息
     *
     * @param cataLogId
     * @return
     */
    @GetMapping(value = "/getSubClass.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取subClass二级目录信息")
    public String getSubClass(String cataLogId) {
        List<SubClass> subClasses = subClassService.listByCataLogId(cataLogId);
        return filterField(subClasses);
    }


    /**
     * 获取type类型
     *
     * @param subClassId
     * @return
     */
    @ApiOperation(value = "获取type类型")
    @GetMapping(value = "/getType.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getType(String subClassId) {
        List<Type> types = typeService.listBySubClassId(subClassId);
        return filterField(types);
    }

    /**
     * 过滤字段
     * @param object
     * @return
     */
    private String filterField(Object object) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter((o, s, o1) -> {
            if ("id".equals(s) || "name".equals(s)) {
                return false;
            } else {
                return true;
            }
        });

        JSONArray jsonArray = JSONArray.fromObject(object, jsonConfig);
        return jsonArray.toString();
    }
}
