package com.example.spring.exception;

import com.example.spring.exception.filter.LogFilter;
import com.example.spring.exception.interceptor.LogInterceptor;
import com.example.spring.exception.resolver.HandlerUserExceptionResolver;
import com.example.spring.exception.resolver.MyExceptionResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyExceptionResolver());
        resolvers.add(new HandlerUserExceptionResolver());
    }

//    @Override // spring configuration 까지 덮어 씌워버리기 땜에 configure 메서드는 사용하지 말고 extend로 시작하는 메서드를 사용할것
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        WebMvcConfigurer.super.configureHandlerExceptionResolvers(resolvers);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/*.ico",
                        "/error",
                        "/error-page/**"
                );
    }

//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        return filterFilterRegistrationBean;
    }
}
