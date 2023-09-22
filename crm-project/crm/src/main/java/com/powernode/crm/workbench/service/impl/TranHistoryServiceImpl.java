package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.TranHistory;
import com.powernode.crm.workbench.mapper.TranHistoryMapper;
import com.powernode.crm.workbench.service.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title:TranHistoryServiceImpl Author liu
 * @Date:2023/4/23 1:19
 * @Version 1.0
 */
@Service("tranHistoryService")
public class TranHistoryServiceImpl implements TranHistoryService {
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    public List<TranHistory> queryTranHistoryForDetailByTranId(String tranId) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(tranId);
    }
}
