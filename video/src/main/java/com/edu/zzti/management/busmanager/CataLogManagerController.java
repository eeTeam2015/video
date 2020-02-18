package com.edu.zzti.management.busmanager;


import com.edu.zzti.common.model.CataLog;
import com.edu.zzti.common.model.Decade;
import com.edu.zzti.common.model.Level;
import com.edu.zzti.common.model.Location;
import com.edu.zzti.common.model.SubClass;
import com.edu.zzti.common.model.Type;
import com.edu.zzti.foreign.service.CataLogService;
import com.edu.zzti.foreign.service.DecadeService;
import com.edu.zzti.foreign.service.LevelService;
import com.edu.zzti.foreign.service.LocService;
import com.edu.zzti.foreign.service.SubClassService;
import com.edu.zzti.foreign.service.TypeService;
import com.edu.zzti.common.model.*;
import com.edu.zzti.foreign.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Api(tags = "目录管理{影视管理:/videoManager}")
@RequestMapping("/catalog/manager")
public class CataLogManagerController {

    @Resource
    private CataLogService cataLogService;

    @Resource
    private SubClassService subClassService;

    @Resource
    private DecadeService decadeService;

    @Resource
    private LevelService levelService;

    @Resource
    private LocService locService;

    @Resource
    private TypeService typeService;

    /**
     * 目录
     */
    @GetMapping(value = {"/catalog.html",""})
    @ApiOperation(value = "目录管理界面")
    public String catalog(ModelMap map) {
        getCatalog(map);
        return "manager/catalog";
    }

    /**
     * 增加目录
     * @param cataLog
     * @return
     */
    @PostMapping(value = "/addCataLog.html")
    @ResponseBody
    @ApiOperation(value = "增加目录")
    public String addCataLog(CataLog cataLog) {
        /**
         * 初始化参数
         */
        cataLog.setIsUse(1);
        String id = cataLogService.add(cataLog);
        return id;
    }




    @PostMapping(value = "/addSubClass.html")
    @ResponseBody
    @ApiOperation(value = "添加二级目录")
    public String addSubClass(SubClass subClass, String cataLogId) {
        /**
         * 初始化参数
         */
        subClass.setIsUse(1);//设置默认在使用

        //设置上级目录
        subClass.setCatalogId(cataLogId);

        //添加二级子类目录
        String id = subClassService.add(subClass);

        return id;
    }

    @PostMapping(value = "/addType.html")
    @ResponseBody
    @ApiOperation(value = "添加类型")
    public String addType(Type type, String subClassId) {
        /**
         * 初始化参数
         */
        type.setIsUse(1);//设置默认在使用

        //设置上级目录
        type.setSubclassId(subClassId);

        //添加二级子类目录
        String id = typeService.add(type);
        return id;
    }

    @PostMapping(value = "/addDecade.html")
    public @ResponseBody
    @ApiOperation(value = "添加年代")
    String addDecade(Decade decade) {
        /**
         * 初始化参数
         */
        decade.setIsUse(1);//设置默认在使用

        /**
         * 添加年代
         */
        String id = decadeService.add(decade);
        return id;
    }

    @PostMapping(value = "/addLevel.html")
    @ResponseBody
    @ApiOperation(value = "添加级别")
    public String addLevel(Level level) {
        /**
         * 初始化参数
         */
        level.setIsUse(1);//设置默认在使用

        /**
         * 添加级别
         */
        String id = levelService.add(level);
        return id;
    }


    @PostMapping(value = "/addLoc.html")
    @ResponseBody
    @ApiOperation(value = "添加地区")
    public String addLoc(Location loc) {
        /**
         * 初始化参数
         */
        loc.setIsUse(1);//设置默认在使用

        /**
         * 添加地区
         */
        String id = locService.add(loc);
        return id;
    }

    private void getCatalog(ModelMap map) {
        List<CataLog> cataLogList = cataLogService.listIsUse();
        List<String> cataLogIdList = cataLogList.stream().map(CataLog::getId).collect(Collectors.toList());

        List<SubClass> subClassList = subClassService.listIsUse(cataLogIdList);
        Map<String, List<SubClass>> subClassMap = subClassList.stream().collect(Collectors.groupingBy(SubClass::getCatalogId));

        for (int i = 0; i < cataLogList.size(); i++) {
            CataLog cataLog = cataLogList.get(i);
            cataLogList.get(i).setSubClassList(subClassMap.get(cataLog.getId()));
        }

        List<Type> typeList = typeService.listIsUse();
        List<Location> locList = locService.listIsUse();
        List<Level> levelList = levelService.listIsUse();
        List<Decade> decadeList = decadeService.listIsUse();

        //读取路径下的文件返回UTF-8类型json字符串
        map.addAttribute("cataLogList", cataLogList);
        map.addAttribute("typeList", typeList);
        map.addAttribute("locList", locList);
        map.addAttribute("levelList", levelList);
        map.addAttribute("decadeList", decadeList);
    }

}
