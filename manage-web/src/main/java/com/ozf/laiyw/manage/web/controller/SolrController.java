package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.web.controller.base.BaseController;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/24 18:21
 */
@SystemLog(description = "全文检索")
@Controller
@RequestMapping("/solr")
public class SolrController extends BaseController {

    @Autowired
    private HttpSolrClient httpSolrClient;
    @Autowired
    private SolrTemplate solrTemplate;

    @SystemLog(description = "SolrJ")
    @RequestMapping("/solrj")
    @ResponseBody
    public Object solrj() {
        return httpSolrClient.getBaseURL();
    }

    @SystemLog(description = "Spring Data Solr")
    @RequestMapping("/springDataSolr")
    @ResponseBody
    public Object springDataSolr() {
        return solrTemplate.getDefaultRequestMethod();
    }
}
