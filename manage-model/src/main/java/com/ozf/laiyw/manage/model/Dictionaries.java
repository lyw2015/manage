package com.ozf.laiyw.manage.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/14 17:54
 */
public class Dictionaries implements Serializable {

    private String id;
    private String name;
    private String identification;//系统引用的标识字段 唯一
    private String description;
    private String updateTime;
    private Integer status;

    private List<DictionariesItem> dictionariesItemList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DictionariesItem> getDictionariesItemList() {
        return dictionariesItemList;
    }

    public void setDictionariesItemList(List<DictionariesItem> dictionariesItemList) {
        this.dictionariesItemList = dictionariesItemList;
    }
}
