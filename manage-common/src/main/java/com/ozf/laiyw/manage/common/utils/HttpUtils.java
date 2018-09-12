package com.ozf.laiyw.manage.common.utils;

import com.ozf.laiyw.manage.common.commons.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static Logger logger = Logger.getLogger(HttpUtils.class);

    public static String doPost(String url, Map<String, String> map) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            if (null != map && !map.keySet().isEmpty()) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (String key : map.keySet()) {
                    list.add(new BasicNameValuePair(key, map.get(key)));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(list, Constants.UTF_8));
            }
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), Constants.UTF_8);
        } catch (Exception ex) {
            logger.error("执行POST请求错误，URL：" + url, ex);
            return null;
        }
    }

    public static String doGet(String url) {
        HttpClient httpClient = null;
        HttpGet httpPost = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), Constants.UTF_8);
        } catch (Exception ex) {
            logger.error("执行GET请求错误，URL：" + url, ex);
            return null;
        }
    }
}
