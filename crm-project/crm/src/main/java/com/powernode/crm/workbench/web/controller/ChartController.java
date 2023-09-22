package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.workbench.domain.FunnelVO;
import com.powernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @title:ChartController Author liu
 * @Date:2023/4/23 20:58
 * @Version 1.0
 */
@Controller
public class ChartController {

    @Autowired
    private TranService tranService;
    @RequestMapping("/workbench/chart/transaction/index.do")
    public String index() {
        //跳转页面
        return "workbench/chart/transaction/index";
    }

    @RequestMapping("/workbench/chart/transaction/queryCountOfTranGroupByStage.do")
    @ResponseBody
    public Object queryCountOfTranGroupByStage() {
        //调用service层方法，查询数据
        List<FunnelVO> funnelVOSList = tranService.queryCountOfTranGroupByStage();
        //根据查询结果，返回响应信息
        return funnelVOSList;
    }

}
