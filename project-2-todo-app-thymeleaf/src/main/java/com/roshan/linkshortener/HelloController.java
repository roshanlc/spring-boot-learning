package com.roshan.linkshortener;


import com.roshan.linkshortener.models.Link;
import com.roshan.linkshortener.utilities.ShortName;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


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
    public Link addNewLink(@RequestParam(value = "link", required = true) String link,HttpServletResponse response) {
        try {
            String shortLink = ShortName.generateShortLink(link);
            template.opsForValue().set(shortLink, link);
            return new Link(shortLink,link);
        }
        catch (Exception e){
            response.setStatus(500);
            return new Link(null,link);
        }
    }

}

