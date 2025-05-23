package com.jm.chat.framework.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConf implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://localhost:3000",
                        "https://azitbase.com"
                ) // 클라이언트 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true) // 쿠키를 포함한 요청 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("Set-Cookie"); // 쿠키 반환 허용
    }
}
