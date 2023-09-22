package com.powernode.crm.settings.web.controller;

import com.powernode.crm.commons.constants.Constants;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @title:UserController Author liu
 * @Date:2023/3/28 17:50
 * @Version 1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * url要和controller方法处理完请求后，响应信息返回的页面的资源目录保持一致
     * 资源名称和方法名保持一致
     * @return
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        //请求转发到登陆页面【目标】
        return "settings/qx/user/login";
    }


    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody     //自动将返回的java对象转化为json对象
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //封装参数
        Map<String,Object> map = new HashMap<String, Object>();
        //key值要和数据库里的#{logAct}保持一致
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层的方法，查询用户
        User user = userService.queryUserByLoginActAndPwd(map);
        //根据查询结果生成相应信息
        ReturnObject returnObject = new ReturnObject();
        if (user == null) {
            //登陆失败，用户名或密码错误
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        }else {
            //进一步判断账号是否合法,判断用户是否过期
            //比较两个时间的字符串，比较两个字符串的大小
            //user.getExpireTime()这个方法默认是字符串格式
            //new Date(),获取当前时间，要转换为字符串格式
            String nowStr = DateUtils.formatDateTime(new Date());//调用日期格式化工具类
            if (nowStr.compareTo(user.getExpireTime()) > 0) {
                //过期，登陆失败
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }else if ("0".equals(user.getLockState())){
                //判断状态是否锁定,0为锁定状态
                //登陆失败，状态被锁定
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("状态被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())) {//拦截的是不在里面的
                //判断ip地址是否安全，通过request对象获取ip
                //getRemoteAddr是用户的远程地址
                //登陆失败，ip受限
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip非法");
            }else {
                //登陆成功，返回json对象
                //map集合效率比较低，底层既有数组又有链表
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);

                //把user对象保存到session作用域中
                session.setAttribute(Constants.SESSION_USER,user);

                //如果需要记住密码，则往外写cookie【cookie值是当前用户的账号和密码】
                if ("true".equals(isRemPwd)) {
                    //账号和密码写两个cookie
                    Cookie c1 = new Cookie("loginAct", user.getLoginAct());
                    c1.setMaxAge(10 * 24 * 60 * 60);//秒
                    //往外写cookie
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd", user.getLoginPwd());
                    c2.setMaxAge(10 * 24 * 60 * 60);
                    response.addCookie(c2);
                }else {
                    //将没有过期的cookie删除
                    Cookie c1 = new Cookie("loginAct", "1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd", "1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")//这是controller的地址
    public String logout(HttpServletResponse response, HttpSession session) {
        //清空cookie
        Cookie c1 = new Cookie("loginAct", "1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd", "1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        //跳转到首页，重定向，地址栏需要改变
        //跳转到登陆页面，重定向必须加上项目的名字
        //请求转发直接写资源名称
        //使用重定向就不会被视图解析器解析了，这里SpringMVC框架帮我们写了项目的名字
        return "redirect:/";

    }

    public static Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7); // 加7天
        return calendar.getTime();
    }

    //注册成功返回登陆页面重新登陆
    @RequestMapping("/settings/qx/user/reg.do")//响应信息返回的页面
    public String saveCreatUser(User user){

        //封装参数（前台没传过来的参数）
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        Date expirationDate = getExpirationDate();
        user.setExpireTime(DateUtils.formatDateTime(expirationDate));
        user.setLockState("1");
        user.setDeptno("A001");
        user.setAllowIps("127.0.0.1");
        user.setCreatetime(DateUtils.formatDateTime(new Date()));
        user.setCreateBy(user.getName());

        try {
            //调用service层方法，保存创建的用户
            int ret = userService.saveCreatUser(user);
            if (ret > 0) {
                System.out.println("用户创建成功");

            }else {
                System.out.println("用户创建失败，请重试");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "settings/qx/user/login";
    }


}
