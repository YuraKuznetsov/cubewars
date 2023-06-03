package org.geekhub.yurii.config;

import freemarker.template.TemplateMethodModelEx;
import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.service.user.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RenderConfig {

    private final AuthService authService;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        final FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths("classpath:/templates/");

        Map<String, Object> sharedVariables = new HashMap<>();

        TemplateMethodModelEx isAdmin = args -> authService.isAdmin();
        sharedVariables.put("isAdmin", isAdmin);

        TemplateMethodModelEx isUser = args -> authService.isUser();
        sharedVariables.put("isUser", isUser);

        TemplateMethodModelEx getUsername = args -> authService.getUsername();
        sharedVariables.put("getUsername", getUsername);

        configurer.setFreemarkerVariables(sharedVariables);

        return configurer;
    }
}