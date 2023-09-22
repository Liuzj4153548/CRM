package com.powernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @title:WorkbenchIndexController Author liu
 * @Date:2023/3/29 15:33
 * @Version 1.0
 */
@Controller
public class WorkbenchIndexController {

    @RequestMapping("/workbench/index.do")
    public String index(){
        //直接跳转到业务主页面
        return "workbench/index";
    }
}
