package com.example.spring.converter.controller;

import com.example.spring.converter.type.IpPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data");

        Integer intData = Integer.valueOf(data);
        System.out.println("intData = " + intData);
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV1(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("ip-port")
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort = " + ipPort);
        return "ok";
    }
}
