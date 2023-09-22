package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @title:ActivityService Author liu
 * @Date:2023/3/30 22:10
 * @Version 1.0
 */
public interface ActivityService {
    int saveCreateActivity(Activity activity);

    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountActivityByCondition(Map<String,Object> map);

    //根据id数组批量删除市场活动
    int deleteActivityByIds(String[] ids);

    //service层调用mapper层的查询方法
    Activity queryActivityById(String id);

    int saveEditActivity(Activity activity);

    List<Activity> queryAllActivities();

    /**
     * service层调用mapper层的sql语句导入所有的市场活动
     * @param activityList
     * @return
     */
    int saveCreatActivityByList(List<Activity> activityList);

    /**
     * 根据id查询一条市场活动的详细信息
     * @param id
     * @return
     */
    Activity queryActivityForDetailById(String id);

    List<Activity> queryActivityForDetailByClueId(String clueId);

    List<Activity> queryActivityForDetailByNameClueId(Map<String,Object> map);

    List<Activity> queryActivityForDetailByIds(String[] ids);

    List<Activity> queryActivityForConvertByNameClueId(Map<String,Object> map);
}
