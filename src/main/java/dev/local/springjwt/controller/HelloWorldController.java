package dev.local.springjwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {

    @RequestMapping({ "/hello" })
    public Map<String,Object> firstPage() {
        Map<String,Object> response = new HashMap<>();
        response.put("version","1.0.0");
        response.put("msg","Hello word");
        return response;
    }

}