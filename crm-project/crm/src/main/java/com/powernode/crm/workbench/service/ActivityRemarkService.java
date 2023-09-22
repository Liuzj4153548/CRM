package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @title:ActivityRemarkService Author liu
 * @Date:2023/4/12 16:02
 * @Version 1.0
 */
public interface ActivityRemarkService {
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    int saveCreateActivityRemark(ActivityRemark remark);

    int deleteActivityRemarkById(String id);

    int saveEditActivityRemark(ActivityRemark remark);
}
