package com.ruoyi.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages = "com.ruoyi.**.mapper", nameGenerator = com.ruoyi.framework.config.FullyQualifiedBeanNameGenerator.class)
public class ApplicationConfig
{
}
