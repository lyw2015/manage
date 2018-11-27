package com.ozf.laiyw.manage.service;

import com.ozf.laiyw.manage.model.Organization;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/16 17:43
 */
public interface OrganizationService {

    List<Organization> getAllOrganization(Organization organization);

    int addOrganization(Organization organization);

    int updateOrganization(Organization organization);

    int updateOrganizationStatus(int id);

    List<Organization> treeOrganization();

    Organization getOrganizationById(int id);
}
