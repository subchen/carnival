/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import javax.annotation.PostConstruct;

@Slf4j
@ConditionalOnClass(name = "scala.Option")
public class ScalaObjectMapperConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Autowired(required = false)
    public void config(ObjectMapper om) {
        if (om != null) {
            om.registerModule(new DefaultScalaModule());
        }
    }

}
