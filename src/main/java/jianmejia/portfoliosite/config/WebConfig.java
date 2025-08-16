// src/main/java/jianmejia/portfoliopage/config/WebConfig.java
package jianmejia.portfoliosite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // WebConfig
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static-uploads/**")
                .addResourceLocations("file:/opt/portfolio/uploads/");
    }


}
