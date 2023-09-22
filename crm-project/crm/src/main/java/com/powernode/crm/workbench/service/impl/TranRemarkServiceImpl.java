package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.TranRemark;
import com.powernode.crm.workbench.mapper.TranRemarkMapper;
import com.powernode.crm.workbench.service.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title:TranRemarkServiceImpl Author liu
 * @Date:2023/4/23 1:07
 * @Version 1.0
 */
@Service("tranRemarkService")
public class TranRemarkServiceImpl implements TranRemarkService {

    @Autowired
    private TranRemarkMapper tranRemarkMapper;
    public List<TranRemark> queryTranRemarkForDetailByTranId(String tranId) {
        return tranRemarkMapper.selectTranRemarkForDetailByTranId(tranId);
    }
}
