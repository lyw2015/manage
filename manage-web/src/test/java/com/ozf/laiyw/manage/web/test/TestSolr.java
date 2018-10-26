package com.ozf.laiyw.manage.web.test;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/24 16:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-servlet.xml", "classpath:spring-solr.xml"})
public class TestSolr {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Test
    public void test() {
        System.out.println(httpSolrClient.getBaseURL());
    }
}
