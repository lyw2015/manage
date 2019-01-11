package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.MenuMapper;
import com.ozf.laiyw.manage.model.Menu;
import com.ozf.laiyw.manage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/10 19:06
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuByUserId(String userId) {
        return menuMapper.getMenuByUserId(userId);
    }

    @Override
    public int saveMenuInfo(Menu menu) {
        menu.setId(StringUtils.randUUID());
        return menuMapper.saveMenuInfo(menu);
    }

    @Override
    public int updateMenuInfo(Menu menu) {
        return menuMapper.updateMenuInfo(menu);
    }

    @Override
    public List<Menu> getRoot() {
        return menuMapper.getRoot();
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }

    @Override
    public List<Menu> getChildrenByParentId(String parentId) {
        return menuMapper.getChildrenByParentId(parentId);
    }

    @Override
    public int removeMenu(String id) {
        List list = menuMapper.getChildrenByParentId(id);
        if (null != list && list.size() > 0) {
            return -1;
        }
        int num = menuMapper.isQuote(id);
        if (num > 0) {
            return -2;
        }
        return menuMapper.removeMenu(id);
    }

    @Override
    public Menu getMenuById(String id) {
        return menuMapper.getMenuById(id);
    }
}
