/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.autoconfig;

import com.github.yingzhuo.carnival.jwt.JwtInfoTransform;
import com.github.yingzhuo.carnival.jwt.JwtTokenGenerator;
import com.github.yingzhuo.carnival.jwt.JwtTokenParser;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import com.github.yingzhuo.carnival.jwt.impl.SimpleJwtTokenGenerator;
import com.github.yingzhuo.carnival.jwt.impl.SimpleJwtTokenParser;
import com.github.yingzhuo.carnival.jwt.mvc.JwtValidatingHandlerInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({
        JwtWebConfiguration.JwtProps.class,
        JwtWebConfiguration.JwtValidatingProps.class
})
@AutoConfigureAfter(JwtBeanConfiguration.class)
public class JwtWebConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtProps props;

    @Autowired
    private JwtValidatingProps jwtValidatingProps;

    @Autowired
    private JwtTokenParser jwtTokenParser;

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenParser jwtTokenParser() {
        return new SimpleJwtTokenParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenGenerator jwtTokenGenerator(JwtInfoTransform transform) {
        return new SimpleJwtTokenGenerator(props.getSignatureAlgorithm(), props.getSecret(), transform);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final JwtValidatingHandlerInterceptor interceptor = new JwtValidatingHandlerInterceptor(
                props.getSignatureAlgorithm(),
                props.getSecret(),
                jwtValidatingProps.getExcludePatternsAsSet(),
                jwtTokenParser
        );

        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    @Data
    @ConfigurationProperties("carnival.jwt")
    static class JwtProps implements InitializingBean {
        private boolean enabled = true;
        private String secret = JwtProps.class.getName();
        private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HMAC256;

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.hasText(getSecret(), "'carnival.jwt.secret' should NOT be null or blank.");
        }
    }

    @Data
    @ConfigurationProperties("carnival.jwt.validating")
    static class JwtValidatingProps {
        private String[] excludePatterns;

        Set<String> getExcludePatternsAsSet() {
            if (excludePatterns == null) {
                return new HashSet<>();
            } else {
                return new HashSet<>(Arrays.asList(excludePatterns));
            }
        }
    }
}
