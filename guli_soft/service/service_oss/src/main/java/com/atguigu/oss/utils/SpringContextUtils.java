package com.atguigu.oss.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext= applicationContext;
    }

    public static AliyunConfig getAliyunConfigBean(){
        return applicationContext.getBean(AliyunConfig.class);
    }
}
