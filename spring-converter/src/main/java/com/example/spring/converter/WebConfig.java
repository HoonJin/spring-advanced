package com.example.spring.converter;

import com.example.spring.converter.converter.IntegerToStringConverter;
import com.example.spring.converter.converter.IpPortToStringConverter;
import com.example.spring.converter.converter.StringToIntegerConverter;
import com.example.spring.converter.converter.StringToIpPortConverter;
import com.example.spring.converter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new MyNumberFormatter());

//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
    }
}
