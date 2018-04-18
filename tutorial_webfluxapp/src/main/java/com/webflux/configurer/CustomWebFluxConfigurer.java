package com.webflux.configurer;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Created by Jimmy. 2018/4/18  16:47
 */
//@Configuration
//@EnableWebFlux
public class CustomWebFluxConfigurer implements WebFluxConfigurer {

    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 配置常用的转换器和格式化配置(与Spring MVC 5配置方式一样)
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加日期格式化转换
        DateFormatter dateFormatter = new DateFormatter(DATE_FORMAT);
        registry.addFormatter(dateFormatter);
    }

    /**
     * 资源路径映射配置(与Spring MVC 5一样,只是引入的类不同)
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/public", "classpath:/static/");

    }
}
