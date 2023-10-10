package com.roshan.linkshortener;


import com.roshan.linkshortener.models.Link;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class HelloController {
    @Autowired
    private RedisTemplate<String, String> template;

    @GetMapping("/{shortLink}")
    public Link getActualLink(@PathVariable(name="shortLink",required = true) String shortLink, HttpServletResponse response) {
        String actualLink = template.opsForValue().get(shortLink);
        if(actualLink == null){
            response.setStatus(404);
        }
        return new Link(shortLink,actualLink);
    }

    @PostMapping(value = "/")
    public Object addNewLink(@RequestParam(value = "link", required = true) String link) {

        return ":ok";
    }


}

