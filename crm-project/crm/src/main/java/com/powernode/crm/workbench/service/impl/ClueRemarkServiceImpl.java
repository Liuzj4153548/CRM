package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.ClueRemark;
import com.powernode.crm.workbench.mapper.ClueRemarkMapper;
import com.powernode.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title:ClueRemarkServiceImpl Author liu
 * @Date:2023/4/16 14:56
 * @Version 1.0
 */
@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;


    public List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId) {
        return clueRemarkMapper.selectClueRemarkForDetailByClueId(clueId);
    }
}
