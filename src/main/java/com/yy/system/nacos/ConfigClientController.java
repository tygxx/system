package com.yy.system.nacos;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {
    
    // @Value("${config.info}")
    // private String configInfo;

    // @GetMapping("/configInfo")
    // public String getConfigInfo() {
    //     return configInfo;
    // }
}