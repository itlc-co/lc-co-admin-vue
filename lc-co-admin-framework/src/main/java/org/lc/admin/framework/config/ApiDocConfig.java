package org.lc.admin.framework.config;

import cn.hutool.core.collection.ListUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;
import org.lc.admin.framework.config.properties.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * Description: api接口文档配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:55
 */
@Configuration
@ConfigurationProperties(prefix = "knife4j")
@Data
public class ApiDocConfig {

    private boolean enable;

    private String pathMapping;

    /**
     * 接口文档docket实例
     *
     * @return docket实例
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                // 用来创建该API文档的基本信息
                .apiInfo(apiInfo())
                // 是否开启swagger
                .enable(enable)
                // 是否响应默认消息
                .useDefaultResponseMessages(false)
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                // .apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))
                //扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                // 设置安全模式，swagger可以设置访问token
                // .securitySchemes(securitySchemes())
                // .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }


    /**
     * Description: 安全模式，这里指定token通过Authorization头请求头传递
     *
     * @return {@link List }<{@link SecurityScheme }> 安全模式列表
     * @date 2022-10-08 18:55
     */
    private List<SecurityScheme> securitySchemes() {
        String key = TokenProperties.getHeader();
        return ListUtil.toList(new ApiKey(key, key, In.HEADER.toValue()));
    }


    /**
     * Description: 安全上下文列表
     *
     * @return {@link List }<{@link SecurityContext }> 安全上下文列表
     * @date 2022-10-08 18:54
     */
    private List<SecurityContext> securityContexts() {
        return ListUtil.toList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build()
        );
    }

    /**
     * Description: 默认的安全上引用
     *
     * @return {@link List }<{@link SecurityReference }> 默认的安全上引用列表
     * @date 2022-10-08 18:54
     */
    private List<SecurityReference> defaultAuth() {
        return ListUtil.toList(new SecurityReference(TokenProperties.getHeader(), new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")}));
    }


    /**
     * api 文档信息
     *
     * @return 接口文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("admin后台管理系统在线API接口文档")
                .contact(new Contact(ApplicationConfig.getName(), null, null))
                .version(ApplicationConfig.getVersion())
                .title("admin后台管理系统在线API接口文档")
                .build();
    }


}
