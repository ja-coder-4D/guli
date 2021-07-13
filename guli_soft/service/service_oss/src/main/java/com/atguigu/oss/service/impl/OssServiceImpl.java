package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.AliyunConfig;
import com.atguigu.oss.utils.SpringContextUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        try {
            AliyunConfig aliyunConfigBean = SpringContextUtils.getAliyunConfigBean();
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint =aliyunConfigBean.getEndpoint();
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = aliyunConfigBean.getKeyid();
            String accessKeySecret = aliyunConfigBean.getKeysecret();
            String bucketName = aliyunConfigBean.getBucketname();
             // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            String datePath =new DateTime().toString("yyyy/MM/dd");
            String fileName = datePath+"/"+UUID.randomUUID().toString().replaceAll("-","") +file.getOriginalFilename();
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, fileName, inputStream);

             // 关闭OSSClient。
            ossClient.shutdown();
            String url = "https://"+bucketName +endpoint+"/"+fileName;
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
