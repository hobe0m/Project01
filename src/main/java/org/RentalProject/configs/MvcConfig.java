package org.RentalProject.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ResourceBundle;

@Configuration
// 설정한 파일의 경로를 추가해서 연결
@EnableConfigurationProperties(FileProperties.class)
public class MvcConfig implements WebMvcConfigurer {

    // 주입 받기
    @Autowired
    private FileProperties fileProperties;

    // 정적 자원에 대한 경로 설정
    // ** : 현재 경로를 포함한 하위 경로 전부
    // fileProperties에 있는 url을 가져온 후, 하위 경로까지 모두 탐색하는 경로 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler(fileProperties.getUrl()
               + "**").addResourceLocations("file:///" + fileProperties.getPath());
               // addResourceLocations : C:uploads01로 연결
               // /를 3개 쓰는 이유는 2개를 쓰면 하나 없어지기 때문이다.

    }

    // 메세지 코드 추가
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.commons", "messages.validation", "messages.errors");

        return ms;
    }
}
