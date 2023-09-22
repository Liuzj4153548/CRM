package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.TranRemark;

import java.util.List;

/**
 * @title:TranRemarkService Author liu
 * @Date:2023/4/23 1:05
 * @Version 1.0
 */
public interface TranRemarkService {

    List<TranRemark> queryTranRemarkForDetailByTranId(String tranId);
}
