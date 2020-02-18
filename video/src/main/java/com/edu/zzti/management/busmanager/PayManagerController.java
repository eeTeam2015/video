package com.edu.zzti.management.busmanager;


import com.edu.zzti.common.model.VipCode;
import com.edu.zzti.foreign.service.VipCodeService;
import com.edu.zzti.common.model.*;
import com.edu.zzti.common.util.Tools;
import com.edu.zzti.foreign.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支付相关管理
 */
@Controller
@Api(tags = "VIP管理{支付管理:/payManager}")
@RequestMapping("/pay/manager")
public class PayManagerController {

    @Resource
    private VipCodeService vipCodeService;

    /**
     * Vip管理
     */
    @GetMapping(value = {"/vipCode.html", ""})
    @ApiOperation(value = "Vip管理页面")
    public String vipCode(ModelMap map) {
        List<VipCode> list = vipCodeService.listIsUse();
        map.addAttribute("vipCodes", list);
        return "manager/vip-manager";
    }

    /**
     * 创建VIP_CODE
     *
     * @param num
     * @return
     */
    @PostMapping(value = "/createVipCode.html")
    @ResponseBody
    @ApiOperation(value = "创建VIP CODE")
    public String createVipCode(String num) {
        JSONObject jsonObject = new JSONObject();
        /**生成VIP号*/
        if (StringUtils.isNotBlank(num)) {
            int n = Integer.parseInt(num);
            VipCode vipCode;
            List<VipCode> vipCodes = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                vipCode = new VipCode();
                vipCode.setCode(Tools.UUID());
                vipCode.setIsUse(1);
                vipCode.setId(Tools.UUID());
                vipCode.setCreateTime(new Date());
                vipCode.setExpireTime(new Date());
                vipCodes.add(vipCode);
            }
            int rtn = vipCodeService.saveAll(vipCodes);
            if (rtn != 0) {
                jsonObject.put("code", "1");
                jsonObject.put("data", vipCodes);
            }
        } else {
            jsonObject.put("code", "0");
        }
        return jsonObject.toString();
    }
}
