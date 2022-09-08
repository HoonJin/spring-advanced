package com.example.spring.converter.converter;

import com.example.spring.converter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        Integer result = conversionService.convert("10", Integer.class);
        Assertions.assertThat(result).isEqualTo(10);

        String result1 = conversionService.convert(10, String.class);
        Assertions.assertThat(result1).isEqualTo("10");

        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        String result2 = conversionService.convert(ipPort, String.class);
        Assertions.assertThat(result2).isEqualTo("127.0.0.1:8080");

        IpPort result3 = conversionService.convert("127.0.0.1:8080", IpPort.class);
        Assertions.assertThat(result3).isEqualTo(new IpPort("127.0.0.1", 8080));
    }
}
