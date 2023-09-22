package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.FunnelVO;
import com.powernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * @title:TranService Author liu
 * @Date:2023/4/21 17:51
 * @Version 1.0
 */
public interface TranService {
    void saveCreateTran(Map<String,Object> map);

    Tran queryTranForDetailById(String id);

    List<FunnelVO> queryCountOfTranGroupByStage();
}
