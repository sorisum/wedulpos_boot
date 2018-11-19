package com.wedul.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 사용을 위한 설정 클래스
 */
@Configuration
@EnableSwagger2
@Profile("debug")
public class SwaggerConfiguration {

  /**
   * 설정
   *
   * @return the docket
   */
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
          .paths(PathSelectors.ant("/user/**")) // /user/** 인 URL들만 추출
          .build();
  }

}
