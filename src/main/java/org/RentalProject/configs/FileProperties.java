package org.RentalProject.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
// path와 url앞의 경로까지 prefix로 설정(연결)
// 항상 file.upload.path or file.upload.url 형식으로 찾는다.
// path와 url 값을 찾는 것을 도와준다.
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {
    private String path;
    private String url;

}
