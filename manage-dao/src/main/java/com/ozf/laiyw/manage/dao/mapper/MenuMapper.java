package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/10 18:28
 */
public interface MenuMapper {

    List<Menu> getMenuByUserId(String userId);

    int saveMenuInfo(Menu menu);

    int updateMenuInfo(Menu menu);

    List<Menu> getRoot();

    List<Menu> getAllMenu();

    List<Menu> getChildrenByParentId(String parentId);

    List<Menu> getUserChildrenByParentId(@Param("parentId") String parentId, @Param("userId") String userId);

    int removeMenu(@Param("id") String id);

    Menu getMenuById(String id);

    int isQuote(String id);
}
