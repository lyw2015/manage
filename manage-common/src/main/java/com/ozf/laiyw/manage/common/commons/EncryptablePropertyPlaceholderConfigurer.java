package com.ozf.laiyw.manage.common.commons;

import com.ozf.laiyw.manage.common.utils.DesEncrypt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/26 12:14
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private final String prefix = "encryption";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        try {
            for (Object key : props.keySet()) {
                if (key.toString().startsWith(prefix)) {
                    props.setProperty(key.toString(), DesEncrypt.decrypt(props.get(key).toString(), DesEncrypt.PASSWORD_CRYPT_KEY));
                }
            }
            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
        }
    }

}
