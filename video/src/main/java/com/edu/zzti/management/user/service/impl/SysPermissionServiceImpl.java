package com.edu.zzti.management.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Multimap;
import com.edu.zzti.common.dao.impl.SysPermissionMapper;
import com.edu.zzti.common.enums.PermissionTypeEnums;
import com.edu.zzti.common.model.SysPermission;
import com.edu.zzti.common.util.EnvUtils;
import com.edu.zzti.management.user.service.SysPermissionService;
import com.edu.zzti.management.user.web.vo.SysPermissionInfoQueryVo;
import com.edu.zzti.management.user.web.vo.SysPermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springfox.documentation.service.*;
import springfox.documentation.spring.web.DocumentationCache;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限信息实现
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {


    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Value("${admin.account.id}")
    private String adminId;

    /**
     * 获取指定id的权限信息
     * @param id
     * @return
     */
    @Override
    public SysPermission getSysPermissionInfo(String id) {
        return sysPermissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 条件查询权限信息
     * @param sysPermission
     * @return
     */
    @Override
    public List<SysPermissionVo> selectSysPermissionInfo(SysPermission sysPermission) {
        List<SysPermission> sysPermissions = sysPermissionMapper.select(sysPermission);
        List<SysPermissionVo> sysPermissionVos = new ArrayList<>();
        for (int i = 0; i < sysPermissions.size(); i++) {
            SysPermissionVo sysPermissionVo = new SysPermissionVo();
            SysPermission sysPermission1 = sysPermissions.get(i);
            BeanUtils.copyProperties(sysPermission1,sysPermissionVo);

            //定义转换类型
            PermissionTypeEnums permissionTypeEnums = PermissionTypeEnums.ofValue(sysPermission1.getType());
            sysPermissionVo.setType(permissionTypeEnums == null ? "" : permissionTypeEnums.getName());
            sysPermissionVos.add(sysPermissionVo);
        }
        return sysPermissionVos;
    }

    /**
     * 更新权限信息
     * @param sysPermissionInfoQueryVo
     */
    @Override
    public void update(SysPermissionInfoQueryVo sysPermissionInfoQueryVo) {
        sysPermissionMapper.updateByPrimaryKeySelective(sysPermissionInfoQueryVo);
    }

    /**
     * 新增权限信息
     * @param sysPermissionInfoQueryVo
     */
    @Override
    public void insert(SysPermissionInfoQueryVo sysPermissionInfoQueryVo) {
        sysPermissionMapper.insertSelective(sysPermissionInfoQueryVo);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        sysPermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysPermission> getMenus() {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setType(PermissionTypeEnums.MENU.getStringValue());
        List<SysPermission> sysPermissions = sysPermissionMapper.select(sysPermission);
        return sysPermissions;
    }

    @Override
    public List<SysPermission> selectSysPermission(List<String> permissionIds) {
        return sysPermissionMapper.selectByIds(permissionIds);
    }

    @Autowired
    private DocumentationCache documentationCache;

    @Override
    public Boolean updatePermissionBySwagger() {
        /**
         * 1.获取swagger的所有路径
         */
        Documentation documentation = documentationCache.documentationByGroup("default");
        Multimap<String, ApiListing> apiListings = documentation.getApiListings();

        /**
         * 2.获取数据所有的已有权限路径
         */
        List<SysPermission> sysPermissions = sysPermissionMapper.select(new SysPermission());
        Map<String, SysPermission> sysPermissionMaps = sysPermissions
                .stream()
                .collect(Collectors.toMap(
                    sysPermission -> sysPermission.getUrl()
                    + ":" + sysPermission.getMethod()
                    + ":" + sysPermission.getType(),
                    s -> s));
        List<SysPermission> addList = new ArrayList<>();
        List<SysPermission> updateList = new ArrayList<>();
        List<SysPermission> deleteList = new ArrayList<>();

        Map<String,String> menus = new HashMap<>(16);
        for (Map.Entry<String, ApiListing> entry : apiListings.entries()) {
            ApiListing value = entry.getValue();

            //获取控制类名称
            StringBuilder parentName = new StringBuilder();
            Set<Tag> tags = value.getTags();
            for (Tag tag : tags) {
                parentName.append(tag.getName());
            }

            /*权限信息管理{用户管理:/user/manager}*/
            String menuId = "0";
            if(parentName.toString().contains("{")){
                menuId = menusOperation(sysPermissionMaps, addList, updateList, menus, parentName);
                parentName = new StringBuilder(parentName.substring(0,parentName.indexOf("{")));
            }else{
                continue;
            }

            String parentKey = value.getResourcePath()+":"+"MENU"+":"+PermissionTypeEnums.MENU.getStringValue();
            SysPermission sysPermissionDbParent = sysPermissionMaps.get(parentKey);
            SysPermission sysPermissionParent = new SysPermission();
            //设置路径类型
            sysPermissionParent.setType(PermissionTypeEnums.MENU.getStringValue());
            //设置路径描述
            sysPermissionParent.setDescription("admin update");
            //设置路径名称
            sysPermissionParent.setName(parentName.toString());
            //设置父id
            sysPermissionParent.setUrl(value.getResourcePath());
            //设置请求方法类型
            sysPermissionParent.setMethod("MENU");
            sysPermissionParent.setPid(menuId);
            if (sysPermissionDbParent != null) {
                sysPermissionParent.setId(sysPermissionDbParent.getId());
                sysPermissionParent.beforeUpdate();
                sysPermissionParent.setUpdateBy(EnvUtils.getCurrentUserId());
                updateList.add(sysPermissionParent);
                sysPermissionMaps.remove(parentKey);
            } else {
                sysPermissionParent.beforeInsert();
                sysPermissionParent.setCreateBy(EnvUtils.getCurrentUserId());
                sysPermissionParent.setUpdateBy(EnvUtils.getCurrentUserId());
                addList.add(sysPermissionParent);
            }

            //获取方法Api信息
            operationHandler(sysPermissionMaps, addList, updateList, value, sysPermissionParent);
        }
        //数据库多余的需要删除的数据
        deleteList.addAll(sysPermissionMaps.values());

        /**
         * 3.进行新增、删除、修改到数据库
         */
        addList.forEach(addSp-> sysPermissionMapper.insertSelective(addSp));
        updateList.forEach(updateSp-> sysPermissionMapper.updateByPrimaryKeySelective(updateSp));
        deleteList.forEach(deleteSp-> sysPermissionMapper.deleteByPrimaryKey(deleteSp.getId()));
        return true;
    }

    @Override
    public PageInfo<SysPermission> selectSysPermissions(SysPermission sysPermission, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<SysPermission> sysPermissions = sysPermissionMapper.select(sysPermission);
        sysPermissions.stream().map(sp->{
            sp.setType(PermissionTypeEnums.ofValue(sp.getType()).getName());
            return sp;
        }).collect(Collectors.toList());
        return new PageInfo<>(sysPermissions);
    }

    @Override
    public List<SysPermission> getByUserId(String userId) {
        if(adminId.equals(userId)){
           return sysPermissionMapper.select(new SysPermission());
        }
        return sysPermissionMapper.getByUserId(userId);
    }

    /**
     * 获取方法多个请求方式处理
     * @param sysPermissionMaps
     * @param addList
     * @param updateList
     * @param value
     * @param sysPermissionParent
     */
    private void operationHandler(Map<String, SysPermission> sysPermissionMaps, List<SysPermission> addList, List<SysPermission> updateList, ApiListing value, SysPermission sysPermissionParent) {
        List<ApiDescription> apis = value.getApis();
        for (ApiDescription api : apis) {
            String path = api.getPath();

            List<Operation> operations = api.getOperations();
            for (Operation operation : operations) {

                String childKey = path+":"+operation.getMethod()+":"+ PermissionTypeEnums.INTERFACE.getStringValue();
                SysPermission sysPermissionDb = sysPermissionMaps.get(childKey);
                SysPermission sysPermission = new SysPermission();
                //设置路径类型
                sysPermission.setType(PermissionTypeEnums.INTERFACE.getStringValue());
                //设置路径描述
                sysPermission.setDescription("admin update");
                //设置路径名称
                sysPermission.setName(operation.getSummary());
                //设置父id
                sysPermission.setPid(sysPermissionParent.getId());
                //设置请求方法类型
                sysPermission.setMethod(operation.getMethod().name());
                //设置路径
                sysPermission.setUrl(path);
                if (sysPermissionDb != null) {
                    sysPermission.setId(sysPermissionDb.getId());
                    sysPermission.beforeUpdate();
                    sysPermission.setUpdateBy(EnvUtils.getCurrentUserId());
                    updateList.add(sysPermission);
                    sysPermissionMaps.remove(childKey);
                } else {
                    sysPermission.beforeInsert();
                    sysPermission.setCreateBy(EnvUtils.getCurrentUserId());
                    sysPermission.setUpdateBy(EnvUtils.getCurrentUserId());
                    addList.add(sysPermission);
                }
            }
        }
    }

    /**
     * 顶级菜单处理
     * @param sysPermissionMaps
     * @param addList
     * @param updateList
     * @param menus
     * @param parentName
     * @return
     */
    private String menusOperation(Map<String, SysPermission> sysPermissionMaps,
                                List<SysPermission> addList,
                                List<SysPermission> updateList,
                                Map<String, String> menus,
                                StringBuilder parentName) {
        String substring = parentName.substring(parentName.indexOf("{")+1, parentName.indexOf("}"));
        String[] split = substring.split("\\:");
        String menuName= split[0];
        String menuUrl= split[1];
        String menuMethod= "MENU";
        String menuType= PermissionTypeEnums.MENU.getStringValue();
        String menuKey = menuUrl+":"+menuMethod+":"+menuType;
        if(menus.get(menuKey)!=null){
            return menus.get(menuKey);
        }
        SysPermission sysPermissionDbMenu = sysPermissionMaps.get(menuKey);
        SysPermission sysPermissionMenu = new SysPermission();
        //设置路径类型
        sysPermissionMenu.setType(PermissionTypeEnums.MENU.getStringValue());
        //设置路径描述
        sysPermissionMenu.setDescription("admin update");
        //设置路径名称
        sysPermissionMenu.setName(menuName);
        //设置父id
        sysPermissionMenu.setUrl(menuUrl);
        //设置请求方法类型
        sysPermissionMenu.setMethod(menuMethod);
        sysPermissionMenu.setPid("0");
        if (sysPermissionDbMenu != null) {
            sysPermissionMenu.setId(sysPermissionDbMenu.getId());
            sysPermissionMenu.beforeUpdate();
            sysPermissionMenu.setUpdateBy(EnvUtils.getCurrentUserId());
            updateList.add(sysPermissionMenu);
            sysPermissionMaps.remove(menuKey);

        } else {
            sysPermissionMenu.beforeInsert();
            sysPermissionMenu.setCreateBy(EnvUtils.getCurrentUserId());
            sysPermissionMenu.setUpdateBy(EnvUtils.getCurrentUserId());
            addList.add(sysPermissionMenu);
        }
        menus.put(menuKey,sysPermissionMenu.getId());
        return sysPermissionMenu.getId();
    }
}
