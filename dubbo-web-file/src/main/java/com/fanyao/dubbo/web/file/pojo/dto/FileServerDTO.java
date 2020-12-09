package com.fanyao.dubbo.web.file.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bugProvider
 * @date: 2020/12/9 14:23
 * @description: 文件服务响应DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileServerDTO {
    private String status;
    private String message;
    private FileServerInfoDTO data;

}
