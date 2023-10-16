package com.roshan.websocketdemo.controllers;

import com.roshan.websocketdemo.models.Greeting;
import com.roshan.websocketdemo.models.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage msg) throws Exception {
        Thread.sleep(1000); // simulating processing delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(msg.getName()) + "!");
    }
}
