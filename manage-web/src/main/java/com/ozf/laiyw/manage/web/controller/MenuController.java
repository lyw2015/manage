package com.ozf.laiyw.manage.web.controller;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.Menu;
import com.ozf.laiyw.manage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/10 19:08
 */
@SystemLog(description = "菜单管理")
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @SystemLog(description = "查看菜单列表")
    @RequestMapping("/getRoot")
    @ResponseBody
    public PageInfo getRoot(PageInfo pageInfo) {
        return menuService.getRoot(pageInfo);
    }

    @RequestMapping("/getAllMenu")
    @ResponseBody
    public WebResult getAllMenu() {
        return WebResult.successResult(menuService.getAllMenu());
    }

    @RequestMapping("/getChildrenByParentId")
    @ResponseBody
    public PageInfo getChildrenByParentId(PageInfo pageInfo, String parentId) {
        return menuService.getChildrenByParentId(pageInfo, parentId);
    }

    @SystemLog(description = "添加菜单")
    @RequestMapping("/saveMenuInfo")
    @ResponseBody
    public WebResult saveMenuInfo(Menu menu) {
        if (StringUtils.isEmpty(menu.getName()))
            return WebResult.errorResult("菜单名称不能为空");
        return WebResult.successResult(menuService.saveMenuInfo(menu));
    }

    @SystemLog(description = "修改菜单")
    @RequestMapping("/updateMenuInfo")
    @ResponseBody
    public WebResult updateMenuInfo(Menu menu) {
        if (StringUtils.isEmpty(menu.getName()))
            return WebResult.errorResult("菜单名称不能为空");
        return WebResult.successResult(menuService.updateMenuInfo(menu));
    }

    @SystemLog(description = "删除菜单")
    @RequestMapping("/removeMenu")
    @ResponseBody
    public WebResult removeMenu(String id) {
        if (StringUtils.isEmpty(id))
            return WebResult.errorResult("请指定需要删除的菜单");
        int count = menuService.removeMenu(id);
        if (count == -1) {
            return WebResult.errorResult("删除失败，该菜单包含子节点");
        }
        return WebResult.successResult();
    }

    @SystemLog(description = "查看菜单详细信息")
    @RequestMapping("/getMenuById")
    @ResponseBody
    public WebResult getMenuById(String id) {
        if (StringUtils.isEmpty(id))
            return WebResult.errorResult("请指定需要查询的菜单");
        return WebResult.successResult(menuService.getMenuById(id));
    }
}
