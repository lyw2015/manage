package com.ozf.laiyw.manage.service;

import com.ozf.laiyw.manage.model.Menu;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/10 19:05
 */
public interface MenuService {

    List<Menu> getMenuByUserId(String userId);

    int saveMenuInfo(Menu menu);

    int updateMenuInfo(Menu menu);

    List<Menu> getRoot();

    List<Menu> getAllMenu();

    List<Menu> getChildrenByParentId(String parentId);

    int removeMenu(String id);

    Menu getMenuById(String id);
}
