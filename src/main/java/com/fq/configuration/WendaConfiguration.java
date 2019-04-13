/*
Date: 04/11,2019, 09:54
*/
package com.fq.configuration;

import com.fq.interceptor.LoginInterceptor;
import com.fq.interceptor.LoginTicketInterceper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WendaConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private LoginTicketInterceper loginTicketInterceper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceper);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/*", "/msg/*");
        super.addInterceptors(registry);
    }
}
