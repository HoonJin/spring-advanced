package com.example.spring.converter.converter;

import com.example.spring.converter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {

    @Override
    public String convert(IpPort source) {
        return source.getIp() + ":" + source.getPort();
    }
}
