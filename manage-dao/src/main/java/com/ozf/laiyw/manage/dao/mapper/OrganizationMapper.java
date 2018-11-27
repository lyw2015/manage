package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.Organization;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/16 16:38
 */
public interface OrganizationMapper {

    List<Organization> getAllOrganization(Organization organization);

    int addOrganization(Organization organization);

    int updateOrganization(Organization organization);

    int updateOrganizationStatus(int id);

    List<Organization> treeOrganization();

    List<Organization> getChildrenByParentId(int parentId);

    Organization getOrganizationById(int id);
}
