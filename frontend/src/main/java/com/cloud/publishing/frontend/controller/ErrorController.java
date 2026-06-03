package com.cloud.publishing.frontend.controller;

import com.cloud.publishing.common.constants.Urls;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    private static final String ERROR_PAGE = "error/404";

    @RequestMapping(Urls.WEB)
    public String error404() {
        return ERROR_PAGE;
    }
}