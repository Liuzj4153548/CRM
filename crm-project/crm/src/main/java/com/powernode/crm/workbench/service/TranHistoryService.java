package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.TranHistory;

import java.util.List;

/**
 * @title:TranHistoryService Author liu
 * @Date:2023/4/23 1:18
 * @Version 1.0
 */
public interface TranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String tranId);
}
