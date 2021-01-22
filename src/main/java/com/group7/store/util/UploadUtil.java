package com.group7.store.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 *
 */
public class UploadUtil {
    private UploadUtil(){}
    private static final Logger log = LoggerFactory.getLogger(UploadUtil.class);

    public static String uploadFile(MultipartFile file, String filePath) {
        String id = UUID.randomUUID().toString();
        id = id.replace("\\-", "");
        try {
            if (file.isEmpty()) {
                log.info("===================上传图片失败！===============");
                return "上传失败";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            if (fileName == null){
                return null;
            }
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 设置文件存储路径
            String path = filePath + id + suffixName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
                log.info("=============创建新的文件夹=====================");
            }
            file.transferTo(dest);// 文件写入
            log.info("====================成功写入=======================");
            return id + suffixName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
}
