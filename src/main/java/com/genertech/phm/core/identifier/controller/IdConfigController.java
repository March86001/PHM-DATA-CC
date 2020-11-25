package com.genertech.phm.core.identifier.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.genertech.phm.log.model.ApiDataCollect;
import com.genertech.phm.log.service.ApiDataCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.genertech.phm.core.identifier.model.IdConfig;
import com.genertech.phm.core.identifier.service.IdConfigService;

/**
 *
 * @author 李翔龙
 *
 */
@Controller
@RequestMapping("/identifier/idconfig")
public class IdConfigController {
    @Resource
    private IdConfigService idConfigService;
    @Autowired
    private ApiDataCollectService apiDataCollectService;

    // 列表
    @RequestMapping(value = { "idconfiglist" }, method = { RequestMethod.POST })
    @ResponseBody
    public Object list() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<IdConfig> idconfiglist = idConfigService.selectAll();
        map.put("rows", idconfiglist);

        return map;

    }
    // 列表
    @RequestMapping(value = { "/logList" }, method = { RequestMethod.POST })
    @ResponseBody
    public Object logList() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ApiDataCollect> apiDataCollectList = apiDataCollectService.searchAll();
        map.put("rows", apiDataCollectList);
        return map;
    }

    // 删除
    @RequestMapping(value = { "idconfigdelete" }, method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> delete(String configCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (configCode != null) {
            String id[] = configCode.split(",");
            for (String element : id) {
                idConfigService.deleteByPrimaryKey(element);
            }
            map.put("result", 1);
        } else {
            map.put("result", 2);
            System.out.println("错误");
        }
        return map;
    }

    // 保存
    @RequestMapping(value = { "idconfigsave" }, method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> save(IdConfig idConfig, String isInsert) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", 2);
        if ("1".equals(isInsert)) {
            idConfigService.insert(idConfig);
            map.put("result", 1);
        } else if ("2".equals(isInsert)) {
            idConfigService.updateByPrimaryKeySelective(idConfig);
            map.put("result", 1);
        }
        return map;
    }

}
