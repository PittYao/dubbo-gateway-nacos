package com.fanyao.common.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bugProvider
 * @date: 2020/12/7 09:34
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtDTO {
    private String token;
}
