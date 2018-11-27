package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.dao.mapper.OrganizationMapper;
import com.ozf.laiyw.manage.model.Organization;
import com.ozf.laiyw.manage.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/16 17:43
 */
@Transactional
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public List<Organization> getAllOrganization(Organization organization) {
        return organizationMapper.getAllOrganization(organization);
    }

    @Override
    public int addOrganization(Organization organization) {
        organization.setUpdateTime(DateUtils.getDateTime());
        return organizationMapper.addOrganization(organization);
    }

    @Override
    public int updateOrganization(Organization organization) {
        organization.setUpdateTime(DateUtils.getDateTime());
        return organizationMapper.updateOrganization(organization);
    }

    @Override
    public int updateOrganizationStatus(int id) {
        List<Organization> organizationList = organizationMapper.getChildrenByParentId(id);
        if (null != organizationList && !organizationList.isEmpty()) {
            return -1;
        }
        // TODO 该机构是否有被引用
        return organizationMapper.updateOrganizationStatus(id);
    }

    @Override
    public List<Organization> treeOrganization() {
        return organizationMapper.treeOrganization();
    }

    @Override
    public Organization getOrganizationById(int id) {
        return organizationMapper.getOrganizationById(id);
    }
}
