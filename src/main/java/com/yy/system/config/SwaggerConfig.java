package com.yy.system.config;

import com.yy.common.config.BaseSwaggerConfig;
import com.yy.common.dto.SwaggerProperties;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder().apiBasePackage("com.yy.system.ops.controller").title("系统管理")
                .description("system相关接口文档").contactName("ty").version("1.0").enableSecurity(true).build();
    }

}