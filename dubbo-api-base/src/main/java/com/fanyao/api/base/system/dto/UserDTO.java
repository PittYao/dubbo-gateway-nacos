package com.fanyao.api.base.system.dto;

import com.fanyao.common.core.dto.CommonDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author: bugProvider
 * @date: 2020/12/3 14:07
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends CommonDTO {

    @NotBlank(message = "账号不能为空")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    private String password;

}
