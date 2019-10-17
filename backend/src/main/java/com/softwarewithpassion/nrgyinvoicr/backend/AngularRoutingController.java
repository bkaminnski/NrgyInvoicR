package com.softwarewithpassion.nrgyinvoicr.backend;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AngularRoutingController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public String forwardToIndexHtml() {
        return "forward:/index.html";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
