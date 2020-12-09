package com.fanyao.dubbo.web.file.controller;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanyao.common.core.enums.ResultEnum;
import com.fanyao.common.core.exception.BusinessException;
import com.fanyao.dubbo.web.file.pojo.dto.FileServerDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${file.upload-path}")
    private String FileUploadPath;

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(),
                    file.getOriginalFilename());

            Map<String, Object> params = new HashMap<>();
            params.put("file", isr);
            params.put("path", "86501729"); // 存储文件的文件夹名字
            params.put("output", "json"); // 返回json格式响应
            params.put("auth_token", "1234"); // 指定生成文件随机token
            String resp = HttpUtil.post(FileUploadPath, params);
            log.info("resp: {}", resp);

            // auth error
          /*   resp: {
              "data": null,
              "message": "auth fail",
              "status": "fail"
            }*/

            // auth success

            // 业务系统 存储 domain + path 两个字段到数据库中，拼接后是下载的地址,不得存url
            // 下载时，(如果fastdfs开启了校验功能)也会请求 /file/token/auth 校验token是否正确，正确才能下载
            // http://localhost:7000/group1/default/20201209/10/30/8/word.png?auth_token=123
           /* resp: {
                "domain": "http://192.168.99.54:7000",
                        "md5": "b388ccb5c1a5187dcacb52778cf19beb",
                        "mtime": 1607476172,
                        "path": "/group1/86501729/timg.jpg",
                        "retcode": 0,
                        "retmsg": "",
                        "scene": "default",
                        "scenes": "default",
                        "size": 5424,
                        "src": "/group1/86501729/timg.jpg",
                        "url": "http://192.168.99.54:7000/group1/86501729/timg.jpg"
            }*/

            JSONObject jsonObject = JSON.parseObject(resp);
            if ("fail".equals(jsonObject.getString("status"))) {
                // 失败
                log.error("文件服务上传失败:{}", resp);
                throw new BusinessException(ResultEnum.FILE_UPLOAD_FAIL);
            } else if (Strings.isNotBlank(jsonObject.getString("md5"))) {
                // 成功
                // TODO 存储 domain, path 两个字段到数据库中.拼接后是下载的地址,注意不得存url
                return jsonObject.getString("domain") + jsonObject.getString("path");
            }


        } catch (IOException e) {
            log.error("文件上传异常:", e);
        }
        throw new BusinessException(ResultEnum.FILE_UPLOAD_FAIL);
    }

    // 校验上传文件的token，下载文件的token，注意：同集群下不会验证token
    // eg: http://localhost:7000/group1/default/20201209/10/30/8/word.png?auth_token=123
    @PostMapping("/token/auth")
    public String checkAuthToken(String auth_token) {
        log.info(auth_token);
        // TODO 比对auth_token是否是文件对应的token，认证成功返回 ok 给 fastdfs
        return "ok";
    }

}
