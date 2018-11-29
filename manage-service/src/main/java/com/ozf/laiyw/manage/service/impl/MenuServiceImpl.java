package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.MenuMapper;
import com.ozf.laiyw.manage.model.Menu;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${menu.cache.key}")
    private String menuCacheKey;

    @PostConstruct
    public void initData() {
        redisCacheUtils.setCacheList(menuCacheKey, menuMapper.getAllMenu());
    }

    @Override
    public int saveMenuInfo(Menu menu) {
        menu.setId(StringUtils.randUUID());
        int count = menuMapper.saveMenuInfo(menu);
        initData();
        return count;
    }

    @Override
    public int updateMenuInfo(Menu menu) {
        int count = menuMapper.updateMenuInfo(menu);
        initData();
        return count;
    }

    @Override
    public List<Menu> getRoot() {
        return menuMapper.getRoot();
    }

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> list = redisCacheUtils.getCacheList(menuCacheKey);
        if (null == list || list.isEmpty()) {
            initData();
            list = redisCacheUtils.getCacheList(menuCacheKey);
        }
        return list;
    }

    @Override
    public List<Menu> getChildrenByParentId(String parentId) {
        return menuMapper.getChildrenByParentId(parentId);
    }

    @Override
    public int removeMenu(String id) {
        List list = menuMapper.getChildrenByParentId(id);
        if (null != list && list.size() > 0)
            return -1;
        int num = menuMapper.isQuote(id);
        if (num > 0) {
            return -2;
        }
        int count = menuMapper.removeMenu(id);
        initData();
        return count;
    }

    @Override
    public Menu getMenuById(String id) {
        return menuMapper.getMenuById(id);
    }
}
