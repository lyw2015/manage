package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.Menu;
import com.ozf.laiyw.manage.service.MenuService;
import com.ozf.laiyw.manage.web.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/10 19:08
 */
@SystemLog(description = "菜单管理")
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MenuService menuService;

    @SystemLog(description = "查看菜单列表")
    @RequestMapping("/getRoot")
    @ResponseBody
    public List<Menu> getRoot() {
        return menuService.getRoot();
    }

    @RequestMapping("/getAllMenu")
    @ResponseBody
    public WebResult getAllMenu() {
        return WebResult.successResult(menuService.getAllMenu());
    }

    @RequestMapping("/getChildrenByParentId")
    @ResponseBody
    public List<Menu> getChildrenByParentId(String parentId) {
        return menuService.getChildrenByParentId(parentId);
    }

    @SystemLog(description = "添加菜单")
    @RequestMapping("/saveMenuInfo")
    @ResponseBody
    public WebResult saveMenuInfo(Menu menu) {
        if (StringUtils.isEmpty(menu.getName()))
            return WebResult.errorResult("菜单名称不能为空");
        try {
            menuService.saveMenuInfo(menu);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("添加菜单错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "修改菜单")
    @RequestMapping("/updateMenuInfo")
    @ResponseBody
    public WebResult updateMenuInfo(Menu menu) {
        if (StringUtils.isEmpty(menu.getName()))
            return WebResult.errorResult("菜单名称不能为空");
        try {
            menuService.updateMenuInfo(menu);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改菜单错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "删除菜单")
    @RequestMapping("/removeMenu")
    @ResponseBody
    public WebResult removeMenu(String id) {
        if (StringUtils.isEmpty(id))
            return WebResult.errorResult("请指定需要删除的菜单");
        try {
            int count = menuService.removeMenu(id);
            if (count == -1) {
                return WebResult.errorResult("删除失败，该菜单包含子节点");
            } else if (count == -2) {
                return WebResult.errorResult("删除失败，该菜单已被引用");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("删除菜单错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
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
