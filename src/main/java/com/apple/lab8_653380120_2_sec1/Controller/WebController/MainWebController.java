package com.apple.lab8_653380120_2_sec1.Controller.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class MainWebController {
    @GetMapping("/")
    public String index() {
        return "index/index";
    }
}
