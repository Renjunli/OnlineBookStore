package com.group7.store.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/22
 */
@Component
public class OssUploadImage {

    protected static final Logger log = LoggerFactory.getLogger(OssUploadImage.class);
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.filedir}")
    private String filedir;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    public String uploadImg2Oss(MultipartFile file) {
        if (file.getSize() > 1024 * 1024 * 20) {
            return "图片太大";
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return null;
        }
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            return "上传失败";
        }
    }

    /**
     * 上传图片获取fileUrl
     *
     * @param instream
     * @param fileName
     * @return
     */
    private String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件

            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase(".jpeg") ||
                filenameExtension.equalsIgnoreCase(".jpg") ||
                filenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (filenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase(".pptx") ||
                filenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase(".xls") ||
                filenameExtension.equalsIgnoreCase(".xlsx")) {
            return "application/vnd.ms-excel";
        }
        if (filenameExtension.equalsIgnoreCase(".docx") ||
                filenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

    /**
     * 获取图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (fileUrl != null) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    /**
     * 多图片上传
     *
     * @param fileList
     * @return
     */
    public String checkList(List<MultipartFile> fileList) {
        String fileUrl = "";
        String str = "";
        StringBuilder photoUrl = new StringBuilder();
        for (int i = 0; i < fileList.size(); i++) {
            fileUrl = uploadImg2Oss(fileList.get(i));
            str = getImgUrl(fileUrl);
            if (i == 0) {
                photoUrl = photoUrl.append(str);
            } else {
                photoUrl.append(",").append(str);
            }
        }
        return photoUrl.toString().trim();
    }

    /**
     * 单个图片上传
     *
     * @param file
     * @return
     */
    public String uploadImg(MultipartFile file) {
        String fileUrl = uploadImg2Oss(file);
        String str = getImgUrl(fileUrl);
        return str.trim();
    }
}

