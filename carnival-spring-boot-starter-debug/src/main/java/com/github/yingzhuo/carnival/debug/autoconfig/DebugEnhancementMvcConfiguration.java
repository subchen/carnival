/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.debug.autoconfig;

import com.github.yingzhuo.carnival.debug.mvc.DebugMvcInterceptor;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.stream.Collectors;

@ConditionalOnWebApplication
@EnableConfigurationProperties(DebugEnhancementMvcConfiguration.Props.class)
public class DebugEnhancementMvcConfiguration extends WebMvcConfigurerAdapter {

    private final Props props;
    private final Environment environment;

    public DebugEnhancementMvcConfiguration(Props props, Environment environment) {
        this.props = props;
        this.environment = environment;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DebugMvcInterceptor(Arrays.stream(environment.getActiveProfiles()).collect(Collectors.toSet()).contains(props.getProfile()))).addPathPatterns("/", "/**");
    }

    @Data
    @ConfigurationProperties("carnival.debug")
    static class Props {
        private String profile = "debug";
    }

}
