package org.RentalProject.RentalService.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
// path와 url앞의 경로까지 prefix로 설정(연결)
// 항상 file.upload.path or file.upload.url 형식으로 찾는다.
// path와 url 값을 찾는 것을 도와준다.
// file.upload.path, file.upload.url이라는 속성이 있으면 각각 path와 url 필드에 매핑
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {
    private String path;
    private String url;

}