/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author 应卓
 * @see SpringUtils
 */
final public class BeanUtils {

    private BeanUtils() {
    }

    public static void registerBean(Class<?> beanClass, String beanId) {
        registerBean(beanClass, beanId, BeanDefinition.SCOPE_SINGLETON);
    }

    public static void registerBean(Class<?> beanClass, String beanId, String scope) {
        registerBean(beanClass, beanId, scope, new MutablePropertyValues());
    }

    public static void registerBean(Class<?> beanClass, String beanId, String scope, MutablePropertyValues mutablePropertyValues) {
        AutowireCapableBeanFactory factory = SpringUtils.getApplicationContext().getAutowireCapableBeanFactory();
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(beanClass);
        bd.setScope(scope);

        if (mutablePropertyValues != null) {
            bd.setPropertyValues(mutablePropertyValues);
        }

        registry.registerBeanDefinition(beanId, bd);
    }

}
