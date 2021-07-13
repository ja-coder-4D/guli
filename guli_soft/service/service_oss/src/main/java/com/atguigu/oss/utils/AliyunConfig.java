package com.atguigu.oss.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class AliyunConfig {

    private String endpoint;

    private String keyid;

    private String keysecret;

    private String bucketname;
}
