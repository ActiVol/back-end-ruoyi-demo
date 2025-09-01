package com.ruoyi.common.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;

/**
 * Minio 配置信息
 *
 * @author ruoyi
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
public class AmazonS3Config {

    /**
     * 用户名
     */
    private static String accessKey;

    /**
     * 密码
     */
    private static String secretKey;
    private static String endpoint;

    private static String domain;
    /**
     * 存储桶名称
     */
    private static String bucketName;


    public static String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        AmazonS3Config.endpoint = endpoint;
    }

    public static String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        AmazonS3Config.domain = domain;
    }

    public static String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        AmazonS3Config.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        AmazonS3Config.secretKey = secretKey;
    }

    public static String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        AmazonS3Config.bucketName = bucketName;
    }

    /**
     * 基于 config 秘钥，构建 S3 客户端的认证信息
     *
     * @return S3 客户端的认证信息
     */
    private static AWSStaticCredentialsProvider buildCredentials() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(accessKey, secretKey));
    }

    /**
     * 构建 S3 客户端的 Endpoint 配置，包括 region、endpoint
     *
     * @return  S3 客户端的 EndpointConfiguration 配置
     */
    private static AwsClientBuilder.EndpointConfiguration buildEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(endpoint,
                null); // 无需设置 region
    }
    @Bean
    public static AmazonS3 getAmazonS3() {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(buildCredentials())
                .withEndpointConfiguration(buildEndpointConfiguration())
                .build();
        return amazonS3;
    }

    /**
     * 删除文件
     * @param fileKey 文件删除
     */
    public static void delFile(String fileKey){
        AmazonS3Client client = (AmazonS3Client) getAmazonS3();
        client.deleteObject(new DeleteObjectRequest(bucketName, fileKey));
    }

    /**
     * 文件上传
     * @param content 文件内容
     * @param path 地址
     * @param type 类型
     * @return
     * @throws Exception
     */
    public static String upload(byte[] content, String path, String type) throws Exception {
        AmazonS3Client client = (AmazonS3Client) getAmazonS3();
        // 元数据，主要用于设置文件类型
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(type);
        objectMetadata.setContentLength(content.length); // 如果不设置，会有 “ No content length specified for stream data” 警告日志
        // 执行上传
        client.putObject(bucketName,
                path, // 相对路径
                new ByteArrayInputStream(content), // 文件内容
                objectMetadata);
        // 拼接返回路径
        return domain + "/" + path;
    }
}
