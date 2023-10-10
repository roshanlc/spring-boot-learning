package com.roshan.linkshortener;


import com.roshan.linkshortener.models.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @Autowired
    private RedisTemplate<String, String> template;


    @GetMapping("/")
    public Object smth() {
        return "stm";
    }


}

