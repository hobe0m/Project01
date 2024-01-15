package org.RentalProject.RentalService.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// 설정한 파일의 경로를 추가해서 연결
@EnableJpaAuditing
/*
 엔터티의 생성일자(@CreatedDate), 수정일자(@LastModifiedDate), 생성자(@CreatedBy),
 수정자(@LastModifiedBy)등의 정보를 자동으로 관리
 설정을 해주어야 @EntityListeners(AuditingEntityListener.class) 사용 가능
 */
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

    // HiddenHttpMethodFilter 메소드를 사용하면, 양식에서 _method를 사용하면 요청 메소드가 변경된다
    // GET, POST 이 외에 PUT, PATCH, DELETE도 사용할 수 있다.
    // Bean을 사용해 스프링 컨테이너에 의해 관리되고 하여, 컨트롤러나 다른 구성 요소에서 이 필터를 사용할 수 있게 한다.
    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
