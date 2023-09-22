package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.commons.constants.Constants;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.DicValue;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.DicValueService;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.domain.Clue;
import com.powernode.crm.workbench.domain.ClueActivityRelation;
import com.powernode.crm.workbench.domain.ClueRemark;
import com.powernode.crm.workbench.service.ActivityService;
import com.powernode.crm.workbench.service.ClueActivityRelationService;
import com.powernode.crm.workbench.service.ClueRemarkService;
import com.powernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @title:ClueController Author liu
 * @Date:2023/4/14 16:56
 * @Version 1.0
 */
@Controller
public class ClueController {

    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        //调用service层方法，查询动态数据
        List<User> userList = userService.queryAllUsers();
        //查询称呼
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        //把数据保存在request作用域中
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        //请求转发线索主页面
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session){
        User user = (User)session.getAttribute(Constants.SESSION_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        clue.setCreateBy(user.getId());

        ReturnObject returnObject = new ReturnObject();

        try {
            //调用service层方法，保存创建的线索
            int ret = clueService.saveCreateClue(clue);
            if (ret > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("数据插入失败，请联系数据库人员进行修复");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("出现异常了，请稍后重试...");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id,HttpServletRequest request){
        //调用service层方法查询数据
        Clue clue = clueService.queryClueForDetailById(id);

        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);

        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        //把数据保存到作用域中
        request.setAttribute("clue",clue);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("activityList",activityList);
        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    @ResponseBody
    public Object queryActivityForDetailByNameClueId(String activityName,String clueId){
         //封装参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //调用service层方法，查询市场活动
        List<Activity> activityList = activityService.queryActivityForDetailByNameClueId(map);
        //根据查询结果，返回响应信息
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveBind.do")
    @ResponseBody
    public Object saveBind(String[] activityId,String clueId) {
        ClueActivityRelation car = null;
        //封装参数，放入list集合中
        List<ClueActivityRelation> relationList = new ArrayList<ClueActivityRelation>();
        //增强for循环
        for (String ai: activityId) {
            car = new ClueActivityRelation();
            car.setActivityId(ai);
            car.setClueId(clueId);
            car.setId(UUIDUtils.getUUID());
            relationList.add(car);
        }
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service方法，批量保存市场活动和线索的关联关系
            int ret = clueActivityRelationService.saveCreateClueActivityRelationByList(relationList);
            if (ret >0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
                returnObject.setRetData(activityList);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("读取失败，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("出异常了，请联系后台工作人员...");
        }

        return returnObject;
    }
    @RequestMapping("/workbench/clue/saveUnbind.do")
    @ResponseBody
    public Object saveUnbind(ClueActivityRelation relation) {
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法，删除线索和市场活动的关联关系
            int ret = clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(relation);
            if (ret > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("数据删除失败，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("删除出异常了，请联系后台工作人员...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String id,HttpServletRequest request){
        //调用service层方法，查询线索的明细信息
        Clue clue = clueService.queryClueForDetailById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("clue",clue);
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/clue/convert";
    }
    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId.do")
    @ResponseBody
    public Object queryActivityForConvertByNameClueId(String activityName,String clueId) {
        //封装参数
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //调用service层方法，查询市场活动
        List<Activity> activityList = activityService.queryActivityForConvertByNameClueId(map);
        //根据查询结果，返回响应信息
        return activityList;
    }

    @RequestMapping("/workbench/clue/convertClue.do")
    @ResponseBody
    public Object convertClue(String clueId,String money,String name,String expectedDate,
                              String stage,String activityId,String isCreateTran,HttpSession session) {
        //封装参数
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        map.put(Constants.SESSION_USER,session.getAttribute(Constants.SESSION_USER));

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法，保存线索转化
            clueService.saveConvertClue(map);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("插入记录出异常了，请联系后台工作人员...");
        }
        return returnObject;
    }
}
