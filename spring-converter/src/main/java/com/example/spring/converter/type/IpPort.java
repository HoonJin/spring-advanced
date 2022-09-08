package com.example.spring.converter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 127.0.0.1:8080
@Getter
@EqualsAndHashCode
@ToString
public class IpPort {

    private String ip;
    private int port;

    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
