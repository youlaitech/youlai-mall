package com.youlai.common.core.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * 运行时入参校验配置
 *
 * @author Ray.Hao
 * @since 2022/11/10
 */
@Configuration
public class ValidationConfig {

    /**
     * 自定义validator实现快速失败
     *
     * @param autowireCapableBeanFactory
     * @return
     */
    @Bean
    public Validator validator( AutowireCapableBeanFactory autowireCapableBeanFactory) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // failFast=true 不校验所有参数，只要出现校验失败情况直接返回，不再进行后续参数校验
                .failFast(true)
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

}
