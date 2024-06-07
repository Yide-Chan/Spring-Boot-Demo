package com.chenyide.minio.controller;

import com.chenyide.minio.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author chenyide
 * @version v1.0
 * @className MinioController
 * @description
 * @date 2024/6/7 17:19
 **/
@Slf4j
@RestController("minio")
public class MinioController {

    @Autowired
    private MinioService minioService;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public void uploadByMinio(MultipartFile file, String bucketName) throws Exception {
        if (file.getSize() < 1){
            log.warn("文件大小为：0");
            return;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        String patchName = this.getPath() + suffix;
        minioService.upload(inputStream, patchName, bucketName, contentType);
    }

    /**
     * 下载文件
     */
    @PostMapping("/download")
    public void downloadByMinio(HttpServletResponse response, String bucketName, String fileName) throws Exception {
        minioService.download(response, bucketName, fileName);
    }

    /**
     * 文件路径
     * @return 返回上传路径
     */
    private String getPath() {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()) + "/" + uuid;
    }
}
