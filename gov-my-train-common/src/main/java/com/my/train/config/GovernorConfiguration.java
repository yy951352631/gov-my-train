/**
 * 
 */
package com.my.train.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */

@Configuration
public class GovernorConfiguration {

    /**
     * 获取国际化信息
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean defaultValidator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/validation/Messages");
        messageSource.setDefaultEncoding("UTF-8");
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }
}
