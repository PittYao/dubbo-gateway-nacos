package com.fanyao.web.base.controller;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.fanyao.api.base.system.dto.SysUserDTO;
import com.fanyao.api.base.system.dto.UserDTO;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.common.core.enums.UserEnum;
import com.fanyao.common.core.exception.BusinessException;
import com.fanyao.common.core.vo.LoginUserVo;
import com.fanyao.web.base.util.dozer.DozerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${file.upload-path}")
    private String FileUploadPath;


    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        String result = "";
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(),
                    file.getOriginalFilename());

            Map<String, Object> params = new HashMap<>();
            params.put("file", isr);
            params.put("path", "86501729");
            params.put("output", "json");
            String resp = HttpUtil.post(FileUploadPath, params);
            Console.log("resp: {}", resp);
            result = resp;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
