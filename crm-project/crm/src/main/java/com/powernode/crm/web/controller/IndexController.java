package com.powernode.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @title:IndexController Author liu
 * @Date:2023/3/28 17:06
 * @Version 1.0
 */
@Controller
public class IndexController {
    /**
     * MVC框架规定：项目名之前的部分http://127.0.0.1:8080/crm部分都必须省略【方便开发人员开发】
     * 该路径是部署项目时的URl
     * 为了简便，协议://ip:port/应用名称必须省去，用/代表应用根路径下的/【指的是webapp】
     * @return
     */
    @RequestMapping("/")
    public String index(){
        //请求转发
        /**
         * 视图解析器中配置了路径的前缀和后缀
         * 前缀：/WEB-INF/pages/省略
         * 后缀：.jsp省略
         */
        return "index";
    }
}
