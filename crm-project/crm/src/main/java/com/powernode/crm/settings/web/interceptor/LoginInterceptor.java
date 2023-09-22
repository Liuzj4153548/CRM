package com.powernode.crm.settings.web.interceptor;

import com.powernode.crm.commons.constants.Constants;
import com.powernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @title:LoginInterceptor Author liu
 * @Date:2023/3/30 17:54
 * @Version 1.0
 */

//登陆验证的拦截器
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求到达目标之前执行
        HttpSession session = request.getSession();
        User user =(User) session.getAttribute(Constants.SESSION_USER);//强转
        if (user == null) {
            //跳转到登陆页面，收订重定向时url必须加上项目的名字，不要写死，动态获取
            //请求转发直接写资源名称
            //response.sendRedirect("/crm");
            response.sendRedirect(request.getContextPath());
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
