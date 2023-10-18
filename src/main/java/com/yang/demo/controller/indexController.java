package com.yang.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class indexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
