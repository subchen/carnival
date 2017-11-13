/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.autoconfig;

import com.github.yingzhuo.carnival.password.Algorithm;
import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(PasswordEncrypterConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.password", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PasswordEncrypterConfiguration {

    private final Props props;

    public PasswordEncrypterConfiguration(Props props) {
        this.props = props;
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncrypter passwordEncrypter() {
        return props.getAlgorithm();
    }

    @Data
    @ConfigurationProperties("carnival.password")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.MD5;
    }

}
