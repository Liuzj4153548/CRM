package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.commons.constants.Constants;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.settings.domain.DicValue;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.DicValueService;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.workbench.domain.Tran;
import com.powernode.crm.workbench.domain.TranHistory;
import com.powernode.crm.workbench.domain.TranRemark;
import com.powernode.crm.workbench.service.CustomerService;
import com.powernode.crm.workbench.service.TranHistoryService;
import com.powernode.crm.workbench.service.TranRemarkService;
import com.powernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @title:TranController Author liu
 * @Date:2023/4/20 21:34
 * @Version 1.0
 */
@Controller
public class TranController {
    //注入数据字典的service
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranService tranService;

    @Autowired
    private TranRemarkService tranRemarkService;

    @Autowired
    private TranHistoryService tranHistoryService;
    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        //调用service层方法，查询动态数据
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");

        //把数据保存到作用域
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/index";
    }
    @RequestMapping("/workbench/transaction/toSave.do")
    public String toSave(HttpServletRequest request){
        //调用service层方法，查询数据
        List<User> userList = userService.queryAllUsers();
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("userList",userList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);

        //请求转发
        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue) {
        //解析properties配置文件，获取可能性
        //文件必须放在resource目录下
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(stageValue);
        //返回响应信息
        return possibility;
    }

    /*测试自动补全的controller
     */
    @RequestMapping("/workbench/transaction/queryCustomerNameByName.do")
    @ResponseBody
    public Object queryCustomerNameByName(String customerName){
        //
        List<String> customerNameList = customerService.queryCustomerNameByName(customerName);
        //根据查询结果，返回响应信息
        return customerNameList;//[xxx,xxxxx,xxxxx...]
    }
    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    @ResponseBody

    public Object saveCreateTran(@RequestParam Map<String,Object> map, HttpSession session){
        //
        map.put(Constants.SESSION_USER,session.getAttribute(Constants.SESSION_USER));
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法保存创建的交易
            tranService.saveCreateTran(map);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("出异常了，请联系工作人员");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String id,HttpServletRequest request) {
        //调用service层方法，查询数据
        Tran tran = tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkForDetailByTranId(id);
        List<TranHistory> historyList = tranHistoryService.queryTranHistoryForDetailByTranId(id);
        //根据交易所处阶段的名称查询可能性
        //tran.getStage()
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());

        //将可能性保存到实体类Tran中
        tran.setPossibility(possibility);


        //把数据保存到作用域中
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("historyList",historyList);
        //request.setAttribute("possibility",possibility);

        //调用service方法，查询所有的阶段
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");

        request.setAttribute("stageList",stageList);

        //请求转发
        return "workbench/transaction/detail";
    }


}

