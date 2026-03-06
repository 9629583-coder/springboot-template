package top.mqxu.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mqxu
 * @date 2026/3/6
 * @description HelloController
 **/
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String getHello() {
        return "Hello SpringBoot!";
    }
}
