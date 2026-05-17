package com.cloud.publishing.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/web/**")
    public String error404() {
        return "error/404";
    }
}