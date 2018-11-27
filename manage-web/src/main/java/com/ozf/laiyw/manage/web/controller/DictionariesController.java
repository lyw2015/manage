package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.Dictionaries;
import com.ozf.laiyw.manage.model.DictionariesItem;
import com.ozf.laiyw.manage.service.DictionariesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/15 14:43
 */
@SystemLog(description = "字典管理")
@RequestMapping("/dictionaries")
@Controller
public class DictionariesController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private DictionariesService dictionariesService;

    @SystemLog(description = "查看数据字典")
    @RequestMapping("/getAllDictionaries")
    @ResponseBody
    public List<Dictionaries> getAllDictionaries() {
        return dictionariesService.getAllDictionaries();
    }

    @RequestMapping("/getDictionariesItemByDid")
    @ResponseBody
    public List<DictionariesItem> getDictionariesItemByDid(String dictId) {
        return dictionariesService.getDictionariesItemByDid(dictId);
    }

    /**
     * 根据标识获取字典项
     *
     * @param identification
     * @return
     */
    @RequestMapping("/getDictionariesItemByIdentification")
    @ResponseBody
    public List<DictionariesItem> getDictionariesItemByIdentification(String identification) {
        return dictionariesService.getDictionariesItemByIdentification(identification);
    }

    @SystemLog(description = "保存数据字典")
    @RequestMapping("/saveDictionaries")
    @ResponseBody
    public WebResult saveDictionaries(Dictionaries dictionaries) {
        if (StringUtils.isEmpty(dictionaries.getName()) || StringUtils.isEmpty(dictionaries.getIdentification())) {
            return WebResult.errorResult("字典名与标识不能为空");
        }
        try {
            dictionariesService.saveDictionaries(dictionaries);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("保存数据字典错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "修改数据字典")
    @RequestMapping("/updateDictionaries")
    @ResponseBody
    public WebResult updateDictionaries(Dictionaries dictionaries) {
        if (StringUtils.isEmpty(dictionaries.getName()) || StringUtils.isEmpty(dictionaries.getIdentification())) {
            return WebResult.errorResult("字典名与标识不能为空");
        }
        if (StringUtils.isEmpty(dictionaries.getId())) {
            return WebResult.errorResult("无效的字典ID");
        }
        try {
            dictionariesService.updateDictionaries(dictionaries);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改数据字典错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "保存数据字典项")
    @RequestMapping("/saveDictionariesItem")
    @ResponseBody
    public WebResult saveDictionariesItem(DictionariesItem dictionariesItem) {
        if (StringUtils.isEmpty(dictionariesItem.getItemName()) || StringUtils.isEmpty(dictionariesItem.getItemValue())) {
            return WebResult.errorResult("字典项名与字典项值不能为空");
        }
        if (StringUtils.isEmpty(dictionariesItem.getDictId())) {
            return WebResult.errorResult("无效的字典ID");
        }
        try {
            dictionariesService.saveDictionariesItem(dictionariesItem);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("保存数据字典项错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "修改数据字典项")
    @RequestMapping("/updateDictionariesItem")
    @ResponseBody
    public WebResult updateDictionariesItem(DictionariesItem dictionariesItem) {
        if (StringUtils.isEmpty(dictionariesItem.getItemName()) || StringUtils.isEmpty(dictionariesItem.getItemValue())) {
            return WebResult.errorResult("字典项名与字典项值不能为空");
        }
        if (StringUtils.isEmpty(dictionariesItem.getDictId())) {
            return WebResult.errorResult("无效的字典ID");
        }
        if (StringUtils.isEmpty(dictionariesItem.getId())) {
            return WebResult.errorResult("无效的字典项ID");
        }
        try {
            dictionariesService.updateDictionariesItem(dictionariesItem);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("修改数据字典项错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @RequestMapping("/getDictionariesById")
    @ResponseBody
    public WebResult getDictionariesById(String id) {
        return WebResult.successResult(dictionariesService.getDictionariesById(id));
    }

    @RequestMapping("/getDictionariesItemById")
    @ResponseBody
    public WebResult getDictionariesItemById(String id) {
        return WebResult.successResult(dictionariesService.getDictionariesItemById(id));
    }

    @SystemLog(description = "删除字典")
    @RequestMapping("/updateDictionariesStatus")
    @ResponseBody
    public WebResult updateDictionariesStatus(String id) {
        try {
            int count = dictionariesService.updateDictionariesStatus(id);
            if (count == -1) {
                return WebResult.errorResult("删除失败，该字典存在字典项");
            }
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("删除字典错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }

    @SystemLog(description = "删除字典项")
    @RequestMapping("/updateDictionariesItemStatus")
    @ResponseBody
    public WebResult updateDictionariesItemStatus(String id) {
        try {
            dictionariesService.updateDictionariesItemStatus(id);
            return WebResult.successResult();
        } catch (Exception e) {
            logger.error("删除字典项错误", e);
            return WebResult.errorNetworkAnomalyResult();
        }
    }
}
