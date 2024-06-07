package com.chenyide.minio.service;

import com.chenyide.minio.enitiy.MinioItem;
import io.minio.messages.Bucket;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenyide
 * @version v1.0
 * @className MinioService
 * @description
 * @date 2024/6/7 17:26
 **/

public interface MinioService {

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    boolean createBucket(String bucketName);


    /**
     * 根据存储桶名称获取信息
     *
     * @param bucketName 存储桶名称
     * @return
     */
    Optional<Bucket> getBucket(String bucketName);

    /**
     * 根据存储桶删除信息
     *
     * @param bucketName 存储桶名称
     */
    void removeBucket(String bucketName);

    /**
     * 根据文件前缀查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     */
    List<MinioItem> getMinioItemsByPrefix(String bucketName, String prefix, Boolean recursive);

    /**
     * 获取文件外链地址
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @param expiry     过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return String
     */
    String getPresignedObjectUrl(String bucketName, String objectName, Integer expiry);

    /**
     * 获取文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    InputStream getObject(String bucketName, String objectName);


    /**
     * 获取全部存储桶
     *
     * @return List<Bucket>
     */
    List<Bucket> getBuckets();

    /**
     * 上传文件
     *
     * @param inputStream inputStream
     * @param objectName  objectName
     * @param bucketName  bucketName
     * @param contentType contentType
     */
    void upload(InputStream inputStream, String objectName, String bucketName, String contentType);

    /**
     * 下载文件
     *
     * @param response   response
     * @param objectName objectName
     */
    void download(HttpServletResponse response, String bucketName, String objectName);

    /**
     * 获取文件url
     *
     * @param objectName objectName
     * @return url
     */
    String getObjectUrl(String bucketName, String objectName);

    /**
     * 获取所有文件
     *
     * @param bucketName bucketName
     */
    List<MinioItem> list(String bucketName);

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名
     */
    void deleteObjectName(String bucketName, String objectName);

    /**
     * 批量删除文件
     *
     * @param bucketName  bucketName
     * @param objectNames 文件名列表
     */
    void deleteObjectNames(String bucketName, List<String> objectNames);

    /**
     * 创建上传文件对象的外链
     *
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @param expiry     过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return uploadUrl
     */
    String createUploadUrl(String bucketName, String objectName, Integer expiry);

    /**
     * 批量创建分片上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param chunkCount 分片数量
     * @param expiry     过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return uploadChunkUrls
     */
    List<String> createUploadChunkUrlList(String bucketName, String objectMD5, Integer chunkCount, Integer expiry);

    /**
     * 创建指定序号的分片文件上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param partNumber 分片序号
     * @param expiry     过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return uploadChunkUrl
     */
    String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber, Integer expiry);

    /**
     * 获取分片文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param prefix     对象名称前缀（ObjectMd5）
     * @param sort       是否排序(升序)
     * @return objectNames
     */
    List<String> listObjectNames(String bucketName, String prefix, Boolean sort);

    /**
     * 获取分片名称地址，HashMap：key=分片序号，value=分片文件地址
     *
     * @param bucketName 存储桶名称
     * @param ObjectMd5  对象Md5
     * @return objectChunkNameMap
     */
    Map<Integer, String> mapChunkObjectNames(String bucketName, String ObjectMd5, Boolean sort);

    /**
     * 合并分片文件成对象文件
     *
     * @param chunkBucKetName   分片文件所在存储桶名称
     * @param composeBucketName 合并后的对象文件存储的存储桶名称
     * @param chunkNames        分片文件名称集合
     * @param objectName        合并后的对象文件名称
     * @return true/false
     */
    boolean composeObject(String chunkBucKetName, String composeBucketName, List<String> chunkNames, String objectName);
}
