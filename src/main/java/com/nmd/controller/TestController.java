package com.nmd.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@EnableAutoConfiguration
public class TestController {

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("name", "Nasreen");
        return "index";
    }

    @GetMapping("/{htmlPage}")
    public String redirect(@PathVariable String htmlPage, Model model) {
        System.out.println("Redirecting to "+htmlPage+".html");
        return htmlPage;
    }
}
