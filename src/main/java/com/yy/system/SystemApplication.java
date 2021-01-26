package com.yy.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
// 如果不配置scanBasePackages则只扫描该工程
// 配置了则会扫描依赖的工程包（由于common工程里面有一个请求日志处理切面，这里要考虑到，所以包名只到yy级别）
@SpringBootApplication(scanBasePackages = "com.yy")
public class SystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

}
