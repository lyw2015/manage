package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.Organization;
import com.ozf.laiyw.manage.service.OrganizationService;
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
 * @Date: 2018/11/16 17:41
 */
@SystemLog(description = "机构管理")
@RequestMapping("/organization")
@Controller
public class OrganizationController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private OrganizationService organizationService;

    @SystemLog(description = "查看机构列表")
    @RequestMapping("/searchOrganization")
    @ResponseBody
    public List<Organization> searchOrganization(Organization organization) {
        return organizationService.getAllOrganization(organization);
    }

    @SystemLog(description = "添加机构")
    @RequestMapping("/addOrganization")
    @ResponseBody
    public WebResult addOrganization(Organization organization) {
        if (StringUtils.isEmpty(organization.getName()) || StringUtils.isEmpty(organization.getFullName())
                || StringUtils.isEmpty(organization.getType())) {
            return WebResult.errorResult("名称与机构类型不能为空");
        }
        try {
            organizationService.addOrganization(organization);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("添加机构错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "修改机构")
    @RequestMapping("/updateOrganization")
    @ResponseBody
    public WebResult updateOrganization(Organization organization) {
        if (StringUtils.isEmpty(organization.getName()) || StringUtils.isEmpty(organization.getFullName())
                || StringUtils.isEmpty(organization.getType())) {
            return WebResult.errorResult("名称与机构类型不能为空");
        }
        if (null == organization.getId()) {
            return WebResult.errorResult("无效机构");
        }
        try {
            organizationService.updateOrganization(organization);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改机构错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @RequestMapping("/treeOrganization")
    @ResponseBody
    public WebResult treeOrganization() {
        try {
            return WebResult.successResult(organizationService.treeOrganization());
        } catch (Exception e) {
            logger.error("获取机构树错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "查看机构详情")
    @RequestMapping("/getOrganizationById")
    @ResponseBody
    public WebResult getOrganizationById(Integer id) {
        if (null == id) {
            return WebResult.errorResult("无效机构");
        }
        try {
            return WebResult.successResult(organizationService.getOrganizationById(id));
        } catch (Exception e) {
            logger.error("查看机构详情错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "删除机构")
    @RequestMapping("/updateOrganizationStatus")
    @ResponseBody
    public WebResult updateOrganizationStatus(Integer id) {
        if (null == id) {
            return WebResult.errorResult("无效机构");
        }
        try {
            int count = organizationService.updateOrganizationStatus(id);
            if (count == -1) {
                return WebResult.errorResult("删除失败，该机构存在子机构");
            }
            if (count == -2) {
                return WebResult.errorResult("删除失败，该机构已被引用");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("删除机构错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }
}
