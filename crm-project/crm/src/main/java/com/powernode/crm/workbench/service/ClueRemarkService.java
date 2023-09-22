package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.ClueRemark;

import java.util.List;

/**
 * @title:ClueRemarkService Author liu
 * @Date:2023/4/16 14:54
 * @Version 1.0
 */
public interface ClueRemarkService {
    List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId);
}
