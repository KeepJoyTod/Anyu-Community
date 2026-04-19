package com.ruoyi.framework.config;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

public class FullyQualifiedBeanNameGenerator implements BeanNameGenerator
{
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry)
    {
        String beanClassName = definition.getBeanClassName();
        if (beanClassName != null)
        {
            return beanClassName;
        }
        if (definition instanceof AnnotatedBeanDefinition)
        {
            AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) definition;
            return annotatedBeanDefinition.getMetadata().getClassName();
        }
        return definition.getBeanClassName();
    }
}
