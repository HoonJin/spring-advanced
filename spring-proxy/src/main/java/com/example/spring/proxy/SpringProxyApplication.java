package com.example.spring.proxy;

import com.example.spring.proxy.config.AppV1Config;
import com.example.spring.proxy.config.AppV2Config;
import com.example.spring.proxy.config.v1.ConcreteProxyConfig;
import com.example.spring.proxy.config.v1.InterfaceProxyConfig;
import com.example.spring.proxy.config.v2.dynamic.DynamicProxyBasicConfig;
import com.example.spring.proxy.config.v2.dynamic.DynamicProxyFilterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import({AppV1Config.class, AppV2Config.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
//@Import(DynamicProxyBasicConfig.class)
@Import(DynamicProxyFilterConfig.class)
@SpringBootApplication(scanBasePackages = "com.example.spring.proxy.app")
public class SpringProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProxyApplication.class);
    }
}
