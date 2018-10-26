package com.ozf.laiyw.manage.web.controller;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.UUID;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/24 18:21
 */
@Controller
@RequestMapping("/solr")
public class SolrController {

    @Autowired
    private HttpSolrClient httpSolrClient;
    @Autowired
    private SolrTemplate solrTemplate;

    @RequestMapping("/solrj")
    @ResponseBody
    public Object solrj() {
        System.out.println(JSON.toJSONString(httpSolrClient.getParser()));
        return httpSolrClient.getBaseURL();
    }

    @RequestMapping("/springDataSolr")
    @ResponseBody
    public Object springDataSolr() {
        System.out.println(JSON.toJSONString(solrTemplate.getSolrCore()));
        return solrTemplate.getDefaultRequestMethod();
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.randUUID());
    }
}
