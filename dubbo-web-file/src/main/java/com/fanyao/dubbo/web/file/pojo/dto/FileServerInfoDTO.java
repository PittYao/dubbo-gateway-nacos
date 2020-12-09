package com.fanyao.dubbo.web.file.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bugProvider
 * @date: 2020/12/9 14:23
 * @description: 文件服务详细信息DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileServerInfoDTO {
    private String domain;
    private String md5;
    private Integer mtime;
    private String path;
    private Integer retcode;
    private String retmsg;
    private String scene;
    private String scenes;
    private Integer size;
    private String src;
    private String url;
}
