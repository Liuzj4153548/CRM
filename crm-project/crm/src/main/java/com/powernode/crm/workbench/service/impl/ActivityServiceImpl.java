package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.mapper.ActivityMapper;
import com.powernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @title:ActivityServiceImpl Author liu
 * @Date:2023/3/30 22:13
 * @Version 1.0
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    public int saveCreateActivity(Activity activity) {


        return activityMapper.insertActivity(activity);

    }

    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    public int queryCountActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    //根据id数组批量删除市场活动
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }


    public int saveEditActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    public List<Activity> queryAllActivities() {
        return activityMapper.selectAllActivities();
    }

    /**
     * service层调用mapper层的sql语句导入所有的市场活动
     * @param activityList
     * @return
     */
    public int saveCreatActivityByList(List<Activity> activityList) {
        return activityMapper.insertActivityByList(activityList);
    }

    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    public List<Activity> queryActivityForDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    public List<Activity> queryActivityForDetailByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForDetailByNameClueId(map);
    }

    public List<Activity> queryActivityForDetailByIds(String[] ids) {
        return activityMapper.selectActivityForDetailByIds(ids);
    }

    public List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameClueId(map);
    }
}
