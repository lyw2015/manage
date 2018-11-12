package com.ozf.laiyw.manage.service;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.model.Menu;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/10 19:05
 */
public interface MenuService {

    int saveMenuInfo(Menu menu);

    int updateMenuInfo(Menu menu);

    PageInfo getRoot(PageInfo pageInfo);

    List<Menu> getAllMenu();

    PageInfo getChildrenByParentId(PageInfo pageInfo, String parentId);

    int removeMenu(String id);

    Menu getMenuById(String id);
}
