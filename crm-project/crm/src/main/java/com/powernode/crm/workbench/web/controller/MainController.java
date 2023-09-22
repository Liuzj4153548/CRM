package com.powernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @title:MainController Author liu
 * @Date:2023/3/30 18:54
 * @Version 1.0
 */
@Controller
public class MainController {

    @RequestMapping("/workbench/main/index.do")
    public String index() {
        //跳转
        return "workbench/main/index";
    }
}
